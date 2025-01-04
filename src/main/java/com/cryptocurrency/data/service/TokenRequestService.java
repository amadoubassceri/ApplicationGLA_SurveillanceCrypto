package com.cryptocurrency.data.service;

/**
 * The TokenRequestService class is a Spring service for handling token requests.
 * Author: Amadou BASS
 */
public class TokenRequestService {

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The token of the user.
     */
    private String token;

    /**
     * Constructs a new TokenRequestService object.
     */
    public TokenRequestService(){}

    /**
     * Constructs a new TokenRequestService object with the given email and token.
     *
     * @param email the email of the user
     * @param token the token of the user
     */
    public TokenRequestService(String email, String token) {
        this.email = email;
        this.token = token;
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
     * Returns the token of the user.
     *
     * @return the token of the user
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token of the user.
     *
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}

