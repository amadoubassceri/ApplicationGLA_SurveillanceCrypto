package com.cryptocurrency.data.service;

import org.springframework.stereotype.Service;

/**
 * The VerifyPasswordMatchesService class is a Spring service for verifying passwords.
 * Author: Amadou BASS
 */
@Service
public class VerifyPasswordMatchesService {

    /**
     * Checks if the given password matches the expected pattern.
     * A valid password must contain at least 8 characters, and must contain at least one lowercase letter, one uppercase letter, one number, and one special character.
     *
     * @param password the password to verify
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être nul ou vide.");
        }

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        System.out.println("Password received: " + password);
        System.out.println("Password matches pattern: " + password.matches(passwordPattern));
        return password.matches(passwordPattern);
    }
}
