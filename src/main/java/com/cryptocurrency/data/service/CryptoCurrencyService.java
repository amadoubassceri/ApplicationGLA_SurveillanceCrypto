package com.cryptocurrency.data.service;

import com.cryptocurrency.data.model.CryptoCurrency;
import com.cryptocurrency.data.repository.CryptoCurrencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * The service for CryptoCurrency objects.
 * Author: Amadou BASS
 */
@Service
public class CryptoCurrencyService {

    /**
     * The repository for CryptoCurrency objects.
     */
    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    /**
     * Returns a CryptoCurrency object by its market cap rank.
     *
     * @param marketCapRank the market cap rank to search for
     * @return a CryptoCurrency object
     * @throws RuntimeException if the CryptoCurrency is not found
     */
    public CryptoCurrency getCryptoCurrencyByMarketCapRank(int marketCapRank) {
        return cryptoCurrencyRepository.findByRank(marketCapRank).
                orElseThrow(() -> new RuntimeException("CryptoCurrency not found"));
    }

    /**
     * Returns a CryptoCurrency object by its symbol.
     *
     * @param symbol the symbol to search for
     * @return a CryptoCurrency object
     * @throws RuntimeException if the CryptoCurrency is not found
     */
    public CryptoCurrency getCryptoCurrencyBySymbol(String symbol) {
        return cryptoCurrencyRepository.findBySymbol(symbol).
                orElseThrow(() -> new RuntimeException("CryptoCurrency not found"));
    }

    /**
     * Returns a CryptoCurrency object by its name.
     *
     * @param name the name to search for
     * @return a CryptoCurrency object
     * @throws RuntimeException if the CryptoCurrency is not found
     */
    public CryptoCurrency getCryptoCurrencyByName(String name) {
        return cryptoCurrencyRepository.findByName(name).
                orElseThrow(() -> new RuntimeException("CryptoCurrency not found"));
    }

    /**
     * Returns a list of all the CryptoCurrency objects in the database.
     *
     * @return a list of CryptoCurrency objects
     */
    public List<CryptoCurrency> getAllCryptoCurrency() {
        return cryptoCurrencyRepository.findAll();
    }

    /**
     * Returns a CryptoCurrency object by its ID.
     *
     * @param id the ID of the CryptoCurrency to retrieve
     * @return a CryptoCurrency object
     */
    public CryptoCurrency getCryptoCurrencyById(Long id) {
        return cryptoCurrencyRepository.findById(id).orElse(null);
    }

    /**
     * Returns a list of all the CryptoCurrency objects with a given price.
     *
     * @param price the price to search for
     * @return a list of CryptoCurrency objects
     */
    public List<CryptoCurrency> findByPrice(Double price) {
        return cryptoCurrencyRepository.findByPrice(price);
    }
}
