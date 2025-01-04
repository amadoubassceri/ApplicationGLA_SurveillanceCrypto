package com.cryptocurrency.data.controller;

import com.cryptocurrency.data.model.Alerts;
import com.cryptocurrency.data.model.User;
import com.cryptocurrency.data.repository.UserRepository;
import com.cryptocurrency.data.service.AlertsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The AlertsController class is a Spring REST controller for managing alerts.
 * Author: Mouhamadou Ahibou DIALLO
 */
@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "http://localhost:3000")
public class AlertsController {

    /**
     * The alertsService field is a Spring service for managing alerts.
     */
    @Autowired
    private AlertsService alertsService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Gets all alerts for a given user.
     *
     * @param userId the id of the user whose alerts are to be retrieved
     * @return a list of alerts for the given user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Alerts>> getAlertsByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        List<Alerts> alerts = alertsService.findByUser(user);
        System.out.println("liste de alerts dans alertController: ");
        if (alerts.isEmpty()) {
            System.out.println("liste vide");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alerts);
    }

    /**
     * Creates a new alert for the given user.
     * @param alert  the alert to be created
     * @return the created alert
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAlert(@RequestBody Alerts alert) {
        try {
            Alerts createdAlert = alertsService.createAlert(alert);
            String nameAlert = createdAlert.getName();
            Map<String, Alerts> response = new HashMap<>();

            System.out.println("done creating alert: ");
            response.put(nameAlert, createdAlert);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Passez à un abonnement Premium")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Updates an existing alert with the given ID.
     *
     * @param id   the ID of the alert to be updated
     * @param updatedAlert the updated alert object
     * @return the updated alert
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAlert(@PathVariable Long id, @RequestBody Alerts updatedAlert) {
        try {
            User user = userRepository.findById(updatedAlert.getUser().getId()).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur ne peut pas être null.");
            }

            Alerts updated = alertsService.updateAlert(id, user, updatedAlert);
            String nameAlert = updated.getName();

            Map<String, Alerts> response = new HashMap<>();
            response.put(nameAlert, updated);
            System.out.println("done updating alert: ");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Deletes an alert with the given ID.
     *
     * @param id the ID of the alert to be deleted
     * @param userId the ID of the user who is deleting the alert
     * @return a response entity indicating the deletion was successful
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAlert(@PathVariable Long id, @RequestParam Long userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur ne peut pas être null.");
            }

            alertsService.deleteAlert(id, user);
            Map<String, Long> response = new HashMap<>();
            response.put("alertId", id);
            response.put("userId", user.getId());

            System.out.println("done deleting alert: ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
