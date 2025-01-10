package com.cryptocurrency.data.controller;

import com.cryptocurrency.data.model.User;
import com.cryptocurrency.data.security.EncodedPassword;
import com.cryptocurrency.data.service.EmailService;
import com.cryptocurrency.data.service.LoginRequestService;
import com.cryptocurrency.data.service.TokenRequestService;
import com.cryptocurrency.data.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UserController class is a Spring REST controller for managing users.
 * Author: Amadou BASS
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    /**
     * The UserService class is a Spring service for managing users.
     */
    @Autowired
    private UserService userService;

    @Autowired
    private  EmailService emailService;

    /**
     * Creates a new user with the given details.
     *
     * @param user The user object containing the details.
     * @return The created user object.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.emailExists(user.getEmail())) {
            System.out.println("cet email existe déja.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("cet email existe déja.");
        }
        if (userService.userNameExists(user.getUsername())) {
            System.out.println("ce nom d'utilisateur existe déja.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ce nom d'utilisateur existe déja.");
        }

        try {
            User createdUser = userService.createUser(user);
            System.out.println("user created: ");

            if (createdUser == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'utilisateur.");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "User created successfully.",
                    "token", createdUser.getTokenHash()
            ));
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Updates a user with the given details.
     *
     * @param id          The id of the user to be updated.
     * @param user        The user object containing the updated details.
     * @return The updated user object.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
    	System.out.println("start updating user in controller: ");
        try {
            User updatedUser = userService.updateUser(id, user);
            System.out.println("user updated in controller: ");

            if (updatedUser == null) {
            	System.out.println("updatingUser in controller is null: ");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du update de l'utilisateur.");
            }
            
            System.out.println("done updating user in controller: ");

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "User updated successfully.",
                    "token", updatedUser.getTokenHash()
            ));
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Updates a user's token hash.
     *
     * @param id The id of the user to be updated.
     * @return The updated user object.
     */
    @PutMapping("/update-token/{id}")
    public ResponseEntity<?> updateToken(@PathVariable Long id) {
        try {
            User updatedUser = userService.updateToken(id);

            if (updatedUser == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du update de l'utilisateur.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "User updated successfully.",
                    "token", updatedUser.getTokenHash()
            ));
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Logs in a user using their email and password.
     *
     * @param loginRequest The user to be logged in.
     * @return The login token for the user.
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestService loginRequest) {
        try {
            String token = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok("Login successful. Token: " + token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Deletes a user with the given id.
     *
     * @param id the id of the user to be deleted
     * @return a success message if the user is deleted successfully
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return A ResponseEntity containing the User object if found, or a not found status if the user does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Returns a list of all users in the database.
     *
     * @return a list of all users in the database
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Verifies if the provided token for a user is valid.
     *
     * @param tokenRequest The request containing the user's email and token.
     * @return A response indicating whether the token is valid.
     */
    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestBody TokenRequestService tokenRequest) {
        boolean isValid = userService.verifyToken(tokenRequest.getEmail(), tokenRequest.getToken());
        if (isValid) {
            User user = userService.findByEmail(tokenRequest.getEmail());

            if (user != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("username", user.getUsername());
                response.put("passwordHash", user.getPasswordHash());
                response.put("email", user.getEmail());
                response.put("tokenHash", user.getTokenHash());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }
    }

    /**
     * Logs out a user using their email.
     *
     * @param email the email of the user to be logged out
     * @return a success message if the user is logged out successfully
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String email) {
        userService.logoutUser(email);
        return ResponseEntity.ok("User logged out successfully");
    }

    /**
     * Handles a forgot password request.
     *
     * @param request the request containing the user's email
     * @return a success message if the email is sent successfully, a not found status if the email is not found, or an internal server error status if an error occurs
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Email non trouvé."));
        }

        String token = user.getTokenHash();


        String resetLink = " http://172.20.144.1:3000/reset-password?token=" + token;
        emailService.sendEmail(email, "Réinitialisation de mot de passe",
                "Cliquez sur ce lien pour réinitialiser votre mot de passe : " + resetLink);

        return ResponseEntity.ok(Map.of("message", "Un email de réinitialisation a été envoyé."));
    }

    /**
     * Resets the password for a user using the provided token.
     *
     * @param request a map containing the token and the new password hash
     * @return a ResponseEntity containing a success message if the password is reset successfully,
     *         or a bad request status with an error message if the token is invalid or expired
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("passwordHash");

        User user = userService.findByTokenHash(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Token invalide ou expiré."));
        }

        user.setPasswordHash(EncodedPassword.encode(newPassword));
        userService.save(user);

        return ResponseEntity.ok(Map.of("message", "Mot de passe réinitialisé avec succès."));
    }

    @PostMapping("/upgrade-to-premium")
    public ResponseEntity<Map<String, String>> upgradeToPremium(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        boolean success = userService.updateUserStatusToPremium(userId);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("success", "true");
        } else {
            response.put("success", "false");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/downgrade-to-standard")
    public ResponseEntity<Map<String, String>> downgradeToStandard(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        boolean success = userService.updateUserStatusToFree(userId);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("success", "true");
        } else {
            response.put("success", "false");
        }
        return ResponseEntity.ok(response);
    }
}

