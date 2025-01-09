package com.cryptocurrency.data.controller;

import com.cryptocurrency.data.model.CryptoCurrency;
import com.cryptocurrency.data.model.CryptoPriceHistory;
import com.cryptocurrency.data.service.CryptoCurrencyService;

import com.cryptocurrency.data.service.DataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The CryptoCurrencyController class is a Spring REST controller for managing cryptocurrencies.
 * Author: Amadou BASS
 */
@RestController
@RequestMapping("/api/cryptocurrencies")
@CrossOrigin(origins = "http://localhost:3000")
public class CryptoCurrencyController {

    /**
     * The service for CryptoCurrency objects.
     */
    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    /**
     * Returns a list of all the cryptocurrencies in the database.
     *
     * @return A list of all the cryptocurrencies in the database.
     */
    @GetMapping
    public ResponseEntity<List<CryptoCurrency>> getAllCryptocurrencies() {
        List<CryptoCurrency> cryptocurrencies = cryptoCurrencyService.getAllCryptoCurrency();
        return ResponseEntity.ok(cryptocurrencies);
    }

    /**
     * Returns a CryptoCurrency object by its ID.
     *
     * @param id The ID of the CryptoCurrency to retrieve.
     * @return A CryptoCurrency object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CryptoCurrency> getCryptoCurrencyById(@PathVariable Long id) {
        System.out.println("just id: " + id);
        CryptoCurrency cryptocurrency = cryptoCurrencyService.getCryptoCurrencyById(id);
        return ResponseEntity.ok(cryptocurrency);
    }

    /**
     * Searches for a CryptoCurrency based on the query parameter.
     * The method attempts to identify the query type: rank, symbol, or name.
     * If the query consists only of digits, it is treated as a market cap rank.
     * If the query length is 5 or less, it is treated as a symbol.
     * Otherwise, it is treated as a name.
     * @param query The search query, which can be a rank, symbol, or name.
     * @return A ResponseEntity containing the CryptoCurrency if found, or a 404 status with a message if not found.
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchCrypto(@RequestParam(value = "query") String query) {
        try {
            CryptoCurrency crypto = null;

            if (query.matches("\\d+")) {
                int rank = Integer.parseInt(query);
                crypto = cryptoCurrencyService.getCryptoCurrencyByMarketCapRank(rank);
            }
            else {
                crypto = cryptoCurrencyService.getCryptoCurrencyBySymbol(query.toUpperCase());
                if (crypto == null) {
                    crypto = cryptoCurrencyService.getCryptoCurrencyByName(query);
                }
            }

            if (crypto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Aucune cryptomonnaie trouvée pour la requête : " + query));
            }
            return ResponseEntity.ok(crypto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Une erreur s'est produite lors de la recherche : " + e.getMessage()));
        }
    }

    /**
     * Returns a list of price history entries for the given cryptocurrency name.
     * The returned list is filtered to include only entries between the given start and end dates.
     * The start and end dates are expected to be in ISO 8601 format.
     * The returned list is ordered by timestamp, with the most recent entries first.
     * @param name The name of the cryptocurrency to retrieve price history for.
     * @param start The start date of the period to retrieve price history for.
     * @param end The end date of the period to retrieve price history for.
     * @return A ResponseEntity containing the filtered list of price history entries.
     */
    @GetMapping("/{name}/price-history")
    public ResponseEntity<List<CryptoPriceHistory>> getPriceHistory(
            @PathVariable String name,
            @RequestParam String start,
            @RequestParam String end
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        List<CryptoPriceHistory> priceHistory = DataCollectionService.getCryptoPriceHistoryMap().get(name);

        if (priceHistory == null || priceHistory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<CryptoPriceHistory> filteredHistory = priceHistory.stream()
                .filter(entry -> !entry.getTimestamp().isBefore(startDate) && !entry.getTimestamp().isAfter(endDate))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredHistory);
    }
}

