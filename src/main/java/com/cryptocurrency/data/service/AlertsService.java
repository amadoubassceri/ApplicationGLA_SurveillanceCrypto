package com.cryptocurrency.data.service;

import com.cryptocurrency.data.model.Alerts;
import com.cryptocurrency.data.model.CryptoCurrency;
import com.cryptocurrency.data.model.User;
import com.cryptocurrency.data.repository.AlertsRepository;

import com.cryptocurrency.data.repository.CryptoCurrencyRepository;
import com.cryptocurrency.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The service for Alerts objects.
 * Author: Amadou BASS
 */
@Service
@Transactional
public class AlertsService {

    /**
     * The repository for Alerts objects.
     */
    @Autowired
    private AlertsRepository alertsRepository;

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * The service for Email objects.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Find all alerts for a given user.
     *
     * @param user the User object to find alerts for
     * @return a list of alerts for the given user
     */
    public List<Alerts> findByUser(User user) {
        User user1 = userRepository.findById(user.getId()).orElse(null);
        return alertsRepository.findByUser(user1);
    }

    /**
     * Find all alerts for a given price threshold.
     *
     * @param priceThreshold the price threshold to find alerts for
     * @return a list of alerts for the given price threshold
     */
    public List<Alerts> findByPriceThreshold(Double priceThreshold) {
        return alertsRepository.findByPriceThreshold(priceThreshold);
    }

    /**
     * Find all alerts for a given variation threshold.
     *
     * @param variationThreshold the variation threshold to find alerts for
     * @return a list of alerts for the given variation threshold
     */
    public List<Alerts> findByVariationThreshold(Double variationThreshold) {
        return alertsRepository.findByVariationThreshold(variationThreshold);
    }

    /**
     * Returns a list of all alerts in the database.
     *
     * @return a list of all alerts in the database
     */
    public List<Alerts> findAll() {
        return alertsRepository.findAll();
    }

    /**
     * Find an alert by its ID.
     *
     * @param id the ID of the alert to find
     * @return the alert with the given ID, or null if none is found
     */
    public Alerts findById(Long id) {
        Optional<Alerts> result = alertsRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * Saves an alert to the database.
     *
     * @param alerts the alert to save
     * @return the saved alert
     */
    public Alerts save(Alerts alerts) {
        return alertsRepository.save(alerts);
    }

    /**
     * Deletes an alert by its ID.
     *
     * @param id the ID of the alert to be deleted
     */
    public void deleteById(Long id) {
        alertsRepository.deleteById(id);
    }

    /**
     * Deletes all alerts associated with the specified user.
     *
     * @param user the user whose alerts are to be deleted
     */
    public void deleteByUser(User user) {
        alertsRepository.deleteByUser(user);
    }

    /**
     * Checks all alerts in the database and sends notifications to users if the current
     * price of the cryptocurrency exceeds their specified price threshold or if the price
     * change percentage exceeds their specified variation threshold.
     * This method retrieves all alerts, iterates through each alert, and checks the
     * current price and change percentage of the associated cryptocurrency. If the
     * conditions are met, it sends an email notification to the user.
     */
    public void checkAlerts() {
        System.out.println("Start checking alerts");
        List<Alerts> alerts = alertsRepository.findAll();

        for (Alerts alert : alerts) {
            List<Alerts> alertsUser = findByUser(alert.getUser());
            CryptoCurrency cryptoCurrency = alert.getCryptoCurrency();

            for (Alerts alertUser : alertsUser) {
                Double currentPrice = cryptoCurrency.getPrice();
                Double changePercentage = cryptoCurrency.getChange();

                if (currentPrice > alertUser.getPriceThreshold()) {
                    emailService.sendNotification(alertUser, currentPrice);
                }

                if (Math.abs(changePercentage) > alertUser.getVariationThreshold()) {
                    emailService.sendNotification(alertUser, changePercentage);
                }
            }
        }
    }

    /**
     * Creates a new alert for the given user.
     *
     * @param alert the alert to create
     * @return the created alert
     */
    public Alerts createAlert(Alerts alert) {
        User user = userRepository.findById(alert.getUser().getId()).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur n'existe pas.");
        }

        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findById(alert.getCryptoCurrency().getId()).orElse(null);

        if (cryptoCurrency == null) {
            throw new IllegalArgumentException("La crypto-monnaie n'existe pas.");
        }

        if ((alert.getPriceThreshold() == null || alert.getVariationThreshold() == null)) {
            throw new IllegalArgumentException("L'alerte doit avoir un seuil défini pour le prix ou la variation.");
        }

        if (checkIfAlertExistsForUser(alert, user)) {
            throw new IllegalArgumentException("Vous avez une alerte similaire pour cette crypto-monnaie.");
        }

        if (checkNombreAlerts(user)) {
            throw new IllegalStateException("Vous avez atteint le nombre maximum d'alertes., Passez à un abonnement Premium.");
        }

        Alerts newAlert = createAlertService(alert, user, cryptoCurrency);
        return alertsRepository.save(newAlert);
    }


    /**
     * Checks if an alert similar to the given alert already exists for the given user.
     *
     * @param alert the alert to check
     * @param user the user to check for
     * @return true if an alert similar to the given alert already exists for the given user, false otherwise
     */
    private boolean checkIfAlertExistsForUser(Alerts alert, User user) {
        List<Alerts> existingAlerts = findByUser(user);
        for (Alerts existingAlert : existingAlerts) {
            if (Objects.equals(existingAlert.getName(), alert.getName()) &&
                    existingAlert.getCryptoCurrency().equals(alert.getCryptoCurrency()) &&
                    Objects.equals(existingAlert.getPriceThreshold(), alert.getPriceThreshold()) &&
                    Objects.equals(existingAlert.getVariationThreshold(), alert.getVariationThreshold())) {
                return true;
            }
        }
        return false;

    }

    /**
     * Creates a new alert.
     *
     * @param alert the alert to be created
     * @return the created alert
     */
    private Alerts createAlertService(Alerts alert, User user, CryptoCurrency cryptoCurrency) {
        Alerts newAlert = new Alerts();

        newAlert.setUser(user);
        newAlert.setName(alert.getName());
        newAlert.setCryptoCurrency(cryptoCurrency);
        newAlert.setPriceThreshold(alert.getPriceThreshold());
        newAlert.setVariationThreshold(alert.getVariationThreshold());

        System.out.println("creation is done");
        System.out.println("new alert: " + newAlert);

        return newAlert;
    }

    /**
     * Checks if the given user has reached the maximum number of alerts.
     *
     * @param user the user to check
     * @return true if the user has reached the maximum number of alerts, false otherwise
     */
    public boolean checkNombreAlerts(User user) {
        List<Alerts> alerts = findByUser(user);
        return alerts.size() == 10 && user.getStatut().equals("normal");
    }

    /**
     * Updates an existing alert for the given user.
     *
     * @param alertId the ID of the alert to update
     * @param user the user who owns the alert
     * @param updatedAlert the updated alert object
     * @return the updated alert
     */
    public Alerts updateAlert(Long alertId, User user, Alerts updatedAlert) {
        Alerts existingAlert = alertsRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alerte introuvable"));

        User user1 = userRepository.findById(user.getId()).orElse(null);
        User existingUser = existingAlert.getUser();

        if (!existingUser.equals(user1)) {
            throw new IllegalStateException("Vous n'êtes pas autorisé à modifier cette alerte.");
        }

        System.out.println("update is done");
        return alertsRepository.save(updateAlertService(existingAlert, updatedAlert));
    }

    private Alerts updateAlertService(Alerts existingAlert, Alerts updatedAlert) {
        existingAlert.setName(updatedAlert.getName());
        existingAlert.setPriceThreshold(updatedAlert.getPriceThreshold());
        existingAlert.setVariationThreshold(updatedAlert.getVariationThreshold());

        System.out.println("update is done");
        return alertsRepository.save(existingAlert);
    }

    /**
     * Deletes an alert with the given ID for the given user.
     *
     * @param alertId the ID of the alert to delete
     * @param user the user who owns the alert
     *
     * @throws RuntimeException if the alert is not found
     * @throws IllegalStateException if the user is not authorized to delete the alert
     */
    public void deleteAlert(Long alertId, User user) {
        Alerts alert = alertsRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alerte introuvable"));

        User user1 = userRepository.findById(user.getId()).orElse(null);

        if (user1 == null) {
            throw new IllegalArgumentException("L'utilisateur n'existe pas.");
        }

        User existingUser = alert.getUser();

        if (!existingUser.equals(user1)) {
            throw new IllegalStateException("Vous n'êtes pas autorisé à supprimer cette alerte.");
        }

        System.out.println("delete is done");
        alertsRepository.delete(alert);
    }
}

