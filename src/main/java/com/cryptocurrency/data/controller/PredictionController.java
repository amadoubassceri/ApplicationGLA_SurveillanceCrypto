package com.cryptocurrency.data.controller;

import com.cryptocurrency.data.model.CryptoPriceHistory;
import com.cryptocurrency.data.service.DataCollectionService;
import com.cryptocurrency.data.service.PredictionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    /**
     * Returns a list of price history entries for the given cryptocurrency name,
     * filtered by the given start and end dates.
     *
     * @param name The name of the cryptocurrency to retrieve price history for.
     * @param start The start date of the period to retrieve price history for.
     * @param end The end date of the period to retrieve price history for.
     * @return A ResponseEntity containing the filtered list of price history entries.
     */
    @GetMapping("/linear-regression")
    public ResponseEntity<List<CryptoPriceHistory>> getLinearRegression(@RequestParam String name, @RequestParam String start, @RequestParam String end) {
        List<CryptoPriceHistory> cryptoPriceHistoryList = DataCollectionService.getCryptoPriceHistoryMap().get(name);
        List<CryptoPriceHistory> predictedPrices = PredictionService.predictNextPricesUsingLinearRegression(cryptoPriceHistoryList);

        if (cryptoPriceHistoryList.isEmpty()) {
            System.out.println("Data exists but list is empty for key: ");
            return ResponseEntity.notFound().build();
        }

        List<CryptoPriceHistory> filteredHistory = getCryptoPriceHistory(name, predictedPrices, start, end);

        return ResponseEntity.ok(filteredHistory);
    }

    /**
     * Returns a list of predicted prices for the given cryptocurrency name,
     * calculated using the moving average model, and filtered by the given start and end dates.
     *
     * @param name The name of the cryptocurrency to retrieve price history for.
     * @param start The start date of the period to retrieve price history for.
     * @param end The end date of the period to retrieve price history for.
     * @return A ResponseEntity containing the filtered list of price history entries.
     */
    @GetMapping("/moving-average")
    public ResponseEntity<List<CryptoPriceHistory>> getMovingAverage(@RequestParam String name, @RequestParam String start, @RequestParam String end) {
        List<CryptoPriceHistory> cryptoPriceHistoryList = DataCollectionService.getCryptoPriceHistoryMap().get(name);
        List<CryptoPriceHistory> predictedPrices = PredictionService.calculateMovingAverages(cryptoPriceHistoryList);

        if (cryptoPriceHistoryList == null) {
            System.out.println("No data found for key: ");
            return ResponseEntity.notFound().build();
        }

        List<CryptoPriceHistory> filteredHistory = getCryptoPriceHistory(name, predictedPrices, start, end);

        return ResponseEntity.ok(filteredHistory);
    }

    /**
     * Returns a list of predicted prices for the given cryptocurrency name,
     * calculated using the linear regression model, and filtered by the given start and end dates.
     * The list also includes the error margins for each predicted price.
     *
     * @param name The name of the cryptocurrency to retrieve price history for.
     * @param start The start date of the period to retrieve price history for.
     * @param end The end date of the period to retrieve price history for.
     * @return A ResponseEntity containing the filtered list of price history entries,
     *         including the predicted prices and their error margins.
     */
    @GetMapping("/marging-error")
    public ResponseEntity<List<CryptoPriceHistory>> getMargingError(@RequestParam String name, @RequestParam String start, @RequestParam String end) {
        List<CryptoPriceHistory> cryptoPriceHistoryList = DataCollectionService.getCryptoPriceHistoryMap().get(name);
        List<CryptoPriceHistory> predictedPrices = PredictionService.predictNextPricesUsingLinearRegression(cryptoPriceHistoryList);
        List<CryptoPriceHistory> margingError = PredictionService.calculateErrorMargins(cryptoPriceHistoryList, predictedPrices);

        if (cryptoPriceHistoryList.isEmpty()) {
            System.out.println("Data exists but list is empty for key: ");
            return ResponseEntity.notFound().build();
        }

        List<CryptoPriceHistory> filteredHistory = getCryptoPriceHistory(name, margingError, start, end);

        return ResponseEntity.ok(filteredHistory);
    }

    /**
     * Filters the given list of CryptoPriceHistory entries for a specific cryptocurrency
     * to include only those between the specified start and end dates.
     *
     * @param name The name of the cryptocurrency whose price history is being filtered.
     * @param cryptoPriceHistoryList The list of CryptoPriceHistory entries to filter.
     * @param start The start date of the period to filter entries, in ISO 8601 format.
     * @param end The end date of the period to filter entries, in ISO 8601 format.
     * @return A list of CryptoPriceHistory entries within the specified date range.
     */
    private List<CryptoPriceHistory> getCryptoPriceHistory(String name, List<CryptoPriceHistory> cryptoPriceHistoryList, String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        System.out.println("Key name: graphe prédictions");
        //System.out.println("CryptoPriceHistoryList: " + DataCollectionService.getCryptoPriceHistoryMap().get(name) + " graphe prédictions");

        return cryptoPriceHistoryList.stream()
                .filter(entry -> !entry.getTimestamp().isBefore(startDate) && !entry.getTimestamp().isAfter(endDate))
                .collect(Collectors.toList());
    }
}
