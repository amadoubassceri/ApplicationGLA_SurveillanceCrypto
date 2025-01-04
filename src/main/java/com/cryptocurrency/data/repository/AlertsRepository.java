package com.cryptocurrency.data.repository;

import com.cryptocurrency.data.model.Alerts;
import com.cryptocurrency.data.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The AlertsRepository interface is a Spring Data JPA repository for managing alerts.
 * AlertsRepository interface.
 * Author: Mouhamadou Ahibou DIALLO
 */
@Repository
public interface AlertsRepository extends JpaRepository<Alerts, Long> {

    /**
     * Find all alerts for a given user.
     *
     * @param user the ID of the user to find alerts for
     * @return a list of alerts for the given user
     */
    List<Alerts> findByUser(User user);

    /**
     * Find all alerts for a given price threshold.
     *
     * @param priceThreshold the price threshold to find alerts for
     * @return a list of alerts for the given price threshold
     */
    List<Alerts> findByPriceThreshold(Double priceThreshold);

    /**
     * Find all alerts for a given variation threshold.
     *
     * @param variationThreshold the variation threshold to find alerts for
     * @return a list of alerts for the given variation threshold
     */
    List<Alerts> findByVariationThreshold(Double variationThreshold);

    /**
     * Delete all alerts for a given user.
     *
     * @param user the user to delete alerts for
     */
    void deleteByUser(User user);
}
