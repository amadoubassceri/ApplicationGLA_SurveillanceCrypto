package com.crypto.service;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataCollectorServiceTest {

    @Mock
    private CoinCapService coinCapService;

    @Mock
    private CryptoPriceRepository repository;

    @InjectMocks
    private DataCollectorService dataCollectorService;

    @Captor
    private ArgumentCaptor<CryptoPrice> cryptoPriceCaptor;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        String mockResponse = """
            {
                "data": [{
                    "symbol": "BTC",
                    "name": "Bitcoin",
                    "priceUsd": "50000.0",
                    "volumeUsd24Hr": "1000000.0",
                    "marketCapUsd": "1000000000.0"
                }]
            }
            """;
        JsonNode mockData = objectMapper.readTree(mockResponse);
        when(coinCapService.getTopCryptos()).thenReturn(mockData);
    }

    @Test
    void collectAndStoreData_ShouldSaveCryptoData() {
        // Execute
        dataCollectorService.collectAndStoreData();

        // Verify
        verify(repository).save(cryptoPriceCaptor.capture());

        CryptoPrice savedCrypto = cryptoPriceCaptor.getValue();
        assertEquals("BTC", savedCrypto.getSymbol());
        assertEquals("Bitcoin", savedCrypto.getName());
        assertEquals(50000.0, savedCrypto.getPrice());
        assertEquals(1000000.0, savedCrypto.getVolume24h());
        assertEquals(1000000000.0, savedCrypto.getMarketCap());
        assertNotNull(savedCrypto.getLastUpdated());
    }

    @Test
    void collectAndStoreData_WhenApiError_ShouldHandleException() {
        // Setup
        when(coinCapService.getTopCryptos()).thenThrow(new RuntimeException("API Error"));

        // Execute
        dataCollectorService.collectAndStoreData();

        // Verify
        verify(repository, never()).save(any());
    }
}