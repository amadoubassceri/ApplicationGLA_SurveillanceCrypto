package com.crypto.service;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataCollectorServiceIntegrationTest {

    @Autowired
    private DataCollectorService dataCollectorService;

    @MockBean
    private CoinCapService coinCapService;

    @Autowired
    private CryptoPriceRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        // Créer un objet ObjectMapper pour convertir la chaîne JSON en JsonNode
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON simulé à utiliser comme réponse du service
        String mockJson = """
            {
              "data": [
                {
                  "symbol": "BTC",
                  "name": "Bitcoin",
                  "priceUsd": "50000.0",
                  "volumeUsd24Hr": "1000000.0",
                  "marketCapUsd": "1000000000.0"
                }
              ]
            }
        """;

        // Convertir le mockJson en un JsonNode
        JsonNode mockData = objectMapper.readTree(mockJson);

        // Simuler la réponse du service CoinCapService
        when(coinCapService.getTopCryptos()).thenReturn(mockData);
    }

    @Test
    void collectAndStoreData_ShouldSaveData_WhenApiReturnsValidData() throws Exception {
        // Exécuter la méthode collectAndStoreData
        dataCollectorService.collectAndStoreData();

        // Vérifiez que les données ont bien été enregistrées
        assertTrue(repository.count() > 0, "Le dépôt devrait contenir des données après l'appel");
    }
}
