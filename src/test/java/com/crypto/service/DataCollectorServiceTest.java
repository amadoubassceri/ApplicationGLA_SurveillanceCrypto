package com.crypto.service;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataCollectorServiceTest {

    @Mock
    private CoinCapService coinCapService;

    @Mock
    private CryptoPriceRepository repository;

    private DataCollectorService dataCollectorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataCollectorService = new DataCollectorService(coinCapService, repository);
    }

    @Test
    void collectAndStoreData_ShouldSaveData_WhenApiReturnsValidData() throws Exception {
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

        JsonNode mockData = new ObjectMapper().readTree(mockJson);

        when(coinCapService.getTopCryptos()).thenReturn(mockData);

        dataCollectorService.collectAndStoreData();

        verify(repository, times(1)).save(any(CryptoPrice.class));
    }
}
