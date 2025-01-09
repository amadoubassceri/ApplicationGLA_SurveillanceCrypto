package com.cryptocurrency.data.model;

import java.time.LocalDateTime;

/**
 * This class represents a CryptoPriceHistory object.
 * Author: Amadou BASS
 */
public class CryptoPriceHistory {

    /**
     * The timestamp of the CryptoPriceHistory object.
     */
    private LocalDateTime timestamp;

    /**
     * The price of the CryptoCurrency at the given timestamp.
     */
    private Double price;

    /**
     * Default constructor for the CryptoPriceHistory object.
     */
    public CryptoPriceHistory() {}

    /**
     * Constructor for the CryptoPriceHistory object.
     *
     * @param timestamp The timestamp of the CryptoPriceHistory object.
     * @param price The price of the CryptoCurrency at the given timestamp.
     */
    public CryptoPriceHistory(LocalDateTime timestamp, Double price) {
        this.timestamp = timestamp;
        this.price = price;
    }

    /**
     * Returns the timestamp of the CryptoPriceHistory object.
     * The timestamp is a LocalDateTime object that represents the date and time
     * when the CryptoPriceHistory object was created.
     *
     * @return The timestamp of the CryptoPriceHistory object.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the price of the CryptoCurrency at the given timestamp.
     * The price is a Double object that represents the price of the CryptoCurrency
     * at the given timestamp.
     *
     * @return The price of the CryptoCurrency at the given timestamp.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the timestamp of the CryptoPriceHistory object.
     * The timestamp is a LocalDateTime object that represents the date and time
     * when the CryptoPriceHistory object was created.
     *
     * @param timestamp The timestamp of the CryptoPriceHistory object.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the price of the CryptoCurrency at the given timestamp.
     *
     * @param price The price of the CryptoCurrency to set.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    public String toString() {
        return "CryptoPriceHistory{" +
                "timestamp=" + timestamp +
                ", price=" + price +
                '}';
    }
}
