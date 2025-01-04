package com.cryptocurrency.data.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * This class represents a User object.
 * Author: Mouhamadou Ahibou DIALLO
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * The ID of the User object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the User object.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The email of the User object.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * The token hash of the User object.
     */
    @Column(nullable = false)
    private String tokenHash;

    /**
     * The password hash of the User object.
     */
    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String statut;

    /**
     * The alerts associated with the User object.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Alerts> alerts;

    /**
     * Default constructor for the User object.
     */
    public User() {}

    /**
     * Constructor for the User object.
     *
     * @param id The ID of the User object.
     * @param username The username of the User object.
     * @param email The email of the User object.
     * @param tokenHash The token hash of the User object.
     * @param passwordHash The password hash of the User object.
     */
    public User(Long id, String username, String email, String tokenHash, String passwordHash) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tokenHash = tokenHash;
        this.passwordHash = passwordHash;
    }

    /**
     * Sets the ID of the User object.
     *
     * @param id The ID of the User object.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the User object.
     *
     * @return The ID of the User object.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the username of the User object.
     *
     * @return The username of the User object.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the User object.
     *
     * @param username The username to set for the User object.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email of the User object.
     *
     * @return The email of the User object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the User object.
     *
     * @param email The email to set for the User object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the token hash of the User object.
     *
     * @return The token hash of the User object.
     */
    public String getTokenHash() {
        return tokenHash;
    }

    /**
     * Sets the token hash of the User object.
     *
     * @param tokenHash The token hash to set for the User object.
     */
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    /**
     * Returns the password hash of the User object.
     *
     * @return The password hash of the User object.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash of the User object.
     *
     * @param passwordHash The password hash to set for the User object.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Converts the User object into a string.
     *
     * @return A string representing the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", tokenHash='" + tokenHash + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }

    /**
     * Sets the status of the User object.
     *
     * @param statut the status to set for the User object
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }

    /**
     * Returns the status of the User object.
     *
     * @return The status of the User object.
     */
    public String getStatut() {
        return statut;
    }
}
