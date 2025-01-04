package com.cryptocurrency.data.service;

import com.cryptocurrency.data.model.CryptoPriceHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The PredictionService class provides methods for making predictions and calculating error margins.
 * Author: Amadou BASS
 */
@Service
public class PredictionService {

    /**
     * Calculates the moving averages of the given cryptocurrency over the given period.
     * This method retrieves the historical price data for the specified cryptocurrency
     * and applies a moving average model to calculate the moving averages.
     *
     * @param priceHistory A list of CryptoPriceHistory objects containing
     *                     historical price data. Must contain at least two data points.
     * @return A list of up to 10 moving averages for the cryptocurrency.
     * @throws IllegalArgumentException If the list contains fewer than two data points.
     */
    public static List<CryptoPriceHistory> calculateMovingAverages(List<CryptoPriceHistory> priceHistory) {
        System.out.println("calculate moving averages");
        List<CryptoPriceHistory> movingAverages = new ArrayList<>();

        Collections.shuffle(priceHistory);
        List<CryptoPriceHistory> randomSubList = priceHistory.subList(0, Math.min(5, priceHistory.size()));

        for (int i = 0; i < randomSubList.size(); i++) {
            double sum = 0;
            for (int j = 0; j <= i; j++) {
                sum += randomSubList.get(j).getPrice();
            }
            movingAverages.add(new CryptoPriceHistory(randomSubList.get(i).getTimestamp(), sum / (i + 1)));
        }

        return movingAverages;
    }

    /**
     * Predicts the next 5 prices of a cryptocurrency using linear regression.
     *
     * This method retrieves the historical price data for the specified cryptocurrency
     * and applies a linear regression model to predict the next 5 prices.
     *
     * @param priceHistory A list of CryptoPriceHistory objects containing
     *                     historical price data. Must contain at least two data points.
     * @return A list of up to 10 predicted prices for the cryptocurrency.
     * @throws IllegalArgumentException If the list contains fewer than two data points.
     */
    public static List<CryptoPriceHistory> predictNextPricesUsingLinearRegression(List<CryptoPriceHistory> priceHistory) {
        System.out.println("predict next prices using linear regression");
        if (priceHistory.size() < 2) {
            throw new IllegalArgumentException("Not enough data points for regression.");
        }

        Collections.shuffle(priceHistory);
        List<CryptoPriceHistory> randomSubList = priceHistory.subList(0, Math.min(5, priceHistory.size()));

        int n = randomSubList.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = i + 1;
            double y = randomSubList.get(i).getPrice();

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }
        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        List<CryptoPriceHistory> predictedPrices = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            predictedPrices.add(new CryptoPriceHistory(priceHistory.get(n - 1).getTimestamp().plusDays(i), slope * (n + i) + intercept));
        }

        return predictedPrices;
    }

    /**
     * Calculates the error margins between actual and predicted prices of a cryptocurrency.
     *
     * This method shuffles the provided lists of actual and predicted prices and calculates
     * the error margins for up to 10 data points. The error margin is the absolute percentage
     * difference between the actual and predicted price.
     *
     * @param actualPrices A list of CryptoPriceHistory objects representing actual historical prices.
     * @param predictedPrices A list of CryptoPriceHistory objects representing predicted prices.
     * @return A list of CryptoPriceHistory objects where each contains the timestamp of the actual
     *         price and the error margin expressed as a percentage.
     */
    public static List<CryptoPriceHistory> calculateErrorMargins(List<CryptoPriceHistory> actualPrices, List<CryptoPriceHistory> predictedPrices) {
        System.out.println("clacule error margins");
        Collections.shuffle(actualPrices);
        Collections.shuffle(predictedPrices);

        List<CryptoPriceHistory> errorMargins = new ArrayList<>();
        for (int i = 0; i < Math.min(actualPrices.size(), 5); i++) {
            double actualPrice = actualPrices.get(i).getPrice();
            double predictedPrice = predictedPrices.get(i).getPrice();

            double margin = Math.abs((predictedPrice - actualPrice) / actualPrice) * 100;
            errorMargins.add(new CryptoPriceHistory(actualPrices.get(i).getTimestamp(), margin));
        }

        return errorMargins;
    }
}
