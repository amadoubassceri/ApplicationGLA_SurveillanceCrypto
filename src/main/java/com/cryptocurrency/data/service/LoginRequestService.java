package com.cryptocurrency.data.service;

import org.springframework.stereotype.Service;

/**
 * The LoginRequestService class is a Spring service for handling login requests.
 * Author: Amadou BASS
 */
@Service
public class LoginRequestService {

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * Constructs a new LoginRequestService object.
     */
    public LoginRequestService(){}

    /**
     * Constructor for the LoginRequestService class.
     *
     * @param email    the email of the user
     * @param password the password of the user
     */
    public LoginRequestService(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

