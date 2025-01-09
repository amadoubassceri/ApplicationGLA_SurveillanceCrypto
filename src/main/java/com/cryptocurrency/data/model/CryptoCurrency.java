package com.cryptocurrency.data.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class represents a CryptoCurrency object.
 * Author: Amadou BASSO
 */
@Entity
@Table(name = "crypto_currency")
public class CryptoCurrency {

    /**
     * The ID of the CryptoCurrency.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the CryptoCurrency.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The symbol of the CryptoCurrency.
     */
    private String symbol;

    /**
     * The market cap rank of the CryptoCurrency.
     */
    @Column(nullable = false, unique = true)
    private int rank;

    /**
     * The supply of the CryptoCurrency.
     */
    private Double supply;

    /**
     * The maximum supply of the CryptoCurrency.
     */
    private Double maxSupply;

    /**
     * The market capitalization of the CryptoCurrency in USD.
     */
    private Double market;

    /**
     * The 24-hour trading volume of the CryptoCurrency in USD.
     */
    private Double volume;

    /**
     * The price of the CryptoCurrency in USD.
     */
    private Double price;

    /**
     * The change in price of the CryptoCurrency in USD over the last 24 hours.
     */
    private Double change;

    /**
     * The volume-weighted average price of the CryptoCurrency in USD over the last 24 hours.
     */
    private Double vwap;

    /**
     * The timestamp of the last update of the CryptoCurrency.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * The alerts associated with the CryptoCurrency.
     */
    @OneToMany(mappedBy = "cryptoCurrency", cascade = CascadeType.ALL)
    private List<Alerts> alerts;

    /**
     * Constructor for the CryptoCurrency class.
     *
     * @param id The ID of the CryptoCurrency.
     * @param name The name of the CryptoCurrency.
     * @param symbol The symbol of the CryptoCurrency.
     * @param rank The market cap rank of the CryptoCurrency.
     */
    public CryptoCurrency(Long id, String name, String symbol, int rank) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
    }

    /**
     * Constructor for the CryptoCurrency class.
     *
     * @param id The ID of the CryptoCurrency.
     * @param name The name of the CryptoCurrency.
     * @param symbol The symbol of the CryptoCurrency.
     * @param rank The market cap rank of the CryptoCurrency.
     * @param supply The supply of the CryptoCurrency.
     * @param maxSupply The maximum supply of the CryptoCurrency.
     * @param market The market capitalization of the CryptoCurrency in USD.
     * @param volume The 24-hour trading volume of the CryptoCurrency in USD.
     * @param price The price of the CryptoCurrency in USD.
     * @param change The change in price of the CryptoCurrency in USD over the last 24 hours.
     * @param vwap The volume-weighted average price of the CryptoCurrency in USD over the last 24 hours.
     * @param timestamp The timestamp of the CryptoCurrency.
     */
    public CryptoCurrency(Long id, String name, String symbol, int rank, Double supply,
                          Double maxSupply, Double market, Double volume, Double price,
                          Double change, Double vwap, LocalDateTime timestamp) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.supply = supply;
        this.maxSupply = maxSupply;
        this.market = market;
        this.volume = volume;
        this.price = price;
        this.change = change;
        this.vwap = vwap;
        this.timestamp = timestamp;
    }

    /**
     * Default constructor for the CryptoCurrency class.
     */
    public CryptoCurrency() {}

    /**
     * Sets the ID of the CryptoCurrency.
     *
     * @param id The ID of the CryptoCurrency.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the CryptoCurrency.
     *
     * @return The ID of the CryptoCurrency.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the name of the CryptoCurrency.
     *
     * @return The name of the CryptoCurrency.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the CryptoCurrency.
     *
     * @param name The name of the CryptoCurrency.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the symbol of the CryptoCurrency.
     *
     * @return The symbol of the CryptoCurrency.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of the CryptoCurrency.
     *
     * @param symbol The symbol of the CryptoCurrency.
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the market capitalization rank of the CryptoCurrency.
     *
     * @return The market capitalization rank of the CryptoCurrency.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets the market capitalization rank of the CryptoCurrency.
     *
     * @param marketCapRank The market capitalization rank to set.
     */
    public void setRank(int marketCapRank) {
        this.rank = marketCapRank;
    }

    /**
     * Returns the total supply of the CryptoCurrency.
     *
     * @return The total supply of the CryptoCurrency.
     */
    public Double getSupply() {
        return supply;
    }

    /**
     * Sets the total supply of the CryptoCurrency.
     *
     * @param supply The total supply to set.
     */
    public void setSupply(Double supply) {
        this.supply = supply;
    }

    /**
     * Returns the maximum supply of the CryptoCurrency.
     *
     * @return The maximum supply of the CryptoCurrency.
     */
    public Double getMaxSupply() {
        return maxSupply;
    }

    /**
     * Sets the maximum supply of the CryptoCurrency.
     *
     * @param maxSupply The maximum supply to set.
     */
    public void setMaxSupply(Double maxSupply) {
        this.maxSupply = maxSupply;
    }

    /**
     * Returns the market capitalization of the CryptoCurrency in USD.
     *
     * @return The market capitalization of the CryptoCurrency in USD.
     */
    public Double getMarket() {
        return market;
    }

    /**
     * Sets the market capitalization of the CryptoCurrency in USD.
     *
     * @param marketCapUsd The market capitalization value to set in USD.
     */
    public void setMarket(Double marketCapUsd) {
        this.market = marketCapUsd;
    }

    /**
     * Returns the 24-hour trading volume of the CryptoCurrency in USD.
     *
     * @return The 24-hour trading volume in USD.
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the 24-hour trading volume of the CryptoCurrency in USD.
     *
     * @param volumeUsd24Hr The 24-hour trading volume to set in USD.
     */
    public void setVolume(Double volumeUsd24Hr) {
        this.volume = volumeUsd24Hr;
    }

    /**
     * Returns the current price of the CryptoCurrency in USD.
     *
     * @return The current price in USD.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the current price of the CryptoCurrency in USD.
     *
     * @param priceUsd The current price to set in USD.
     */
    public void setPrice(Double priceUsd) {
        this.price = priceUsd;
    }

    /**
     * Returns the percentage change in the CryptoCurrency's price over the
     * last 24 hours.
     *
     * @return The percentage change in the CryptoCurrency's price over the
     * last 24 hours.
     */
    public Double getChange() {
        return change;
    }

    /**
     * Sets the percentage change in the CryptoCurrency's price over the
     * last 24 hours.
     *
     * @param changePercent24Hr The percentage change in the CryptoCurrency's
     * price over the last 24 hours.
     */
    public void setChange(Double changePercent24Hr) {
        this.change = changePercent24Hr;
    }

    /**
     * Returns the volume weighted average price of the CryptoCurrency over the
     * last 24 hours.
     *
     * @return The volume weighted average price of the CryptoCurrency over the
     * last 24 hours.
     */
    public Double getVwap() {
        return vwap;
    }

    /**
     * Sets the volume weighted average price of the CryptoCurrency over the
     * last 24 hours.
     *
     * @param vwap24Hr The volume weighted average price of the CryptoCurrency
     * over the last 24 hours.
     */
    public void setVwap(Double vwap24Hr) {
        this.vwap = vwap24Hr;
    }

    /**
     * Returns the timestamp of the CryptoCurrency object.
     *
     * @return The timestamp of the CryptoCurrency object.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the CryptoCurrency object.
     *
     * @param timestamp The timestamp to set for the CryptoCurrency object.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
