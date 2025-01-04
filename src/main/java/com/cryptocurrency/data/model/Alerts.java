package com.cryptocurrency.data.model;

import jakarta.persistence.*;

/**
 * This class represents an Alerts object.
 * Author: Mouhamadou Ahibou DIALLO
 */
@Entity
@Table(name = "alert")
public class Alerts {

    /**
     * The ID of the Alerts object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user associated with the Alerts object.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The market data associated with the Alerts object.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crypto_id", nullable = false)
    private CryptoCurrency cryptoCurrency;

    /**
     * The name associated with the Alerts object.
     */
    @Column(nullable = false, name = "name")
    private String name;

    /**
     * The price threshold associated with the Alerts object.
     */
    @Column(nullable = false, name = "price_threshold")
    private Double priceThreshold;

    /**
     * The variation threshold associated with the Alerts object.
     */
    @Column(nullable = false, name = "variation_threshold")
    private Double variationThreshold;

    /**
     * Default constructor for the Alerts class.
     */
    public Alerts() {}


    /**
     * Constructor for the Alerts class.
     *
     * @param id The ID of the Alerts object.
     * @param user The user associated with the Alerts object.
     * @param cryptoCurrency The cryptocurrency associated with the Alerts object.
     * @param priceThreshold The price threshold associated with the Alerts object.
     * @param variationThreshold The variation threshold associated with the Alerts object.
     */
    public Alerts(Long id, User user, CryptoCurrency cryptoCurrency, Double priceThreshold, Double variationThreshold) {
        this.id = id;
        this.user = user;
        this.cryptoCurrency = cryptoCurrency;
        this.priceThreshold = priceThreshold;
        this.variationThreshold = variationThreshold;
    }

    /**
     * Returns the name associated with the Alerts object.
     *
     * @return The name associated with the Alerts object.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with the Alerts object.
     *
     * @param name The name to set for the Alerts object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user associated with the Alerts object.
     *
     * @return The user associated with the Alerts object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the Alerts object.
     *
     * @param user The user associated with the Alerts object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the ID of the Alerts object.
     *
     * @param id The ID of the Alerts object.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the Alerts object.
     *
     * @return The ID of the Alerts object.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the CryptoCurrency object associated with the Alerts object.
     *
     * @return The CryptoCurrency object associated with the Alerts object.
     */
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    /**
     * Sets the CryptoCurrency object associated with the Alerts object.
     *
     * @param cryptoCurrency The CryptoCurrency object to associate with the Alerts object.
     */
    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    /**
     * Returns the price threshold associated with the Alerts object.
     *
     * @return The price threshold associated with the Alerts object.
     */
    public Double getPriceThreshold() {
        return priceThreshold;
    }

    /**
     * Sets the price threshold associated with the Alerts object.
     *
     * @param priceThreshold The price threshold associated with the Alerts object.
     */
    public void setPriceThreshold(Double priceThreshold) {
        this.priceThreshold = priceThreshold;
    }

    /**
     * Returns the variation threshold associated with the Alerts object.
     *
     * @return The variation threshold associated with the Alerts object.
     */
    public Double getVariationThreshold() {
        return variationThreshold;
    }

    /**
     * Sets the variation threshold associated with the Alerts object.
     *
     * @param variationThreshold The variation threshold to set for the Alerts object.
     */
    public void setVariationThreshold(Double variationThreshold) {
        this.variationThreshold = variationThreshold;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Alerts{" +
                "id=" + id +
                ", user=" + user +
                ", cryptoCurrency=" + cryptoCurrency +
                ", name='" + name + '\'' +
                ", priceThreshold=" + priceThreshold +
                ", variationThreshold=" + variationThreshold +
                '}';
    }
}