/*
package com.crypto.service;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredictionService {

    private final CryptoPriceRepository cryptoPriceRepository;

    public PredictionService(CryptoPriceRepository cryptoPriceRepository) {
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    public double calculateSimpleMovingAverage(String symbol, int period) {
        List<CryptoPrice> prices = cryptoPriceRepository.findTop10ByOrderByIdAsc();

        List<Double> lastPrices = prices.stream()
                .filter(p -> p.getSymbol().equals(symbol))
                .limit(period)
                .map(CryptoPrice::getPrice)
                .collect(Collectors.toList());

        return lastPrices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public double predictNextPrice(String symbol) {
        double sma = calculateSimpleMovingAverage(symbol, 5);
        // Logique de prédiction simpliste basée sur la moyenne mobile
        return sma * 1.02; // Prédiction avec une augmentation de 2%
    }
}*/
