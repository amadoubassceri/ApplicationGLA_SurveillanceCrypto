package com.cryptocurrency.data.repository;

import com.cryptocurrency.data.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The CryptoCurrencyRepository interface is a Spring Data JPA repository for managing cryptocurrency data.
 * CryptoCurrencyRepository interface.
 * Author: Amadou BASS
 */
@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {


    /**
     * Find a cryptocurrency by its symbol.
     *
     * @param symbol the symbol of the cryptocurrency to find
     * @return an Optional containing the cryptocurrency if found, or an empty Optional if not
     */
    Optional<CryptoCurrency> findBySymbol(String symbol);


    /**
     * Find a cryptocurrency by its name.
     *
     * @param name the name of the cryptocurrency to find
     * @return an Optional containing the cryptocurrency if found, or an empty Optional if not
     */
    Optional<CryptoCurrency> findByName(String name);

    /**
     * Find a cryptocurrency by its market capitalization rank.
     *
     * @param rank the market capitalization rank of the cryptocurrency to find
     * @return an Optional containing the cryptocurrency if found, or an empty Optional if not
     */
    Optional<CryptoCurrency> findByRank(int rank);

    /**
     * Find all cryptocurrencies with a given price.
     *
     * @param price the price to search for
     * @return a list of all cryptocurrencies with the given price
     */
    List<CryptoCurrency> findByPrice(Double price);
}