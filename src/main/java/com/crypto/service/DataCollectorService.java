package com.crypto.service;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;




@Service
@Slf4j
@RequiredArgsConstructor
public class DataCollectorService {

    private final CoinCapService coinCapService;
    private final CryptoPriceRepository repository;

    @Scheduled(fixedRateString = "${collector.interval}")
    public void collectAndStoreData() {
        try {
            JsonNode data = coinCapService.getTopCryptos();

            if (data != null && data.has("data")) {
                data.get("data").forEach(crypto -> {
                    CryptoPrice price = new CryptoPrice();
                    price.setSymbol(crypto.get("symbol").asText());
                    price.setName(crypto.get("name").asText());
                    price.setPrice(crypto.get("priceUsd").asDouble());
                    price.setVolume24h(crypto.get("volumeUsd24Hr").asDouble());
                    price.setMarketCap(crypto.get("marketCapUsd").asDouble());
                    price.setLastUpdated(LocalDateTime.now());

                    repository.save(price);
                });

                log.info("Données collectées avec succès");
            }
        } catch (Exception e) {
            log.error("Erreur lors de la collecte des données", e);
        }
    }
}