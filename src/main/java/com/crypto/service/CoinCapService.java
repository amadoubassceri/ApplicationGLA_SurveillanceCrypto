package com.crypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CoinCapService {

    @Value("${coincap.api.url}")
    private String apiUrl;

    @Value("${coincap.api.limit}")
    private int limit;

    private final RestTemplate restTemplate;

    // Constructor for injecting dependencies
    public CoinCapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JsonNode getTopCryptos() {
        try {
            String url = String.format("%s/assets?limit=%d", apiUrl, limit);
            return restTemplate.getForObject(url, JsonNode.class);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des données depuis CoinCap", e);
            throw new RuntimeException("Erreur API CoinCap", e);
        }
    }
}
