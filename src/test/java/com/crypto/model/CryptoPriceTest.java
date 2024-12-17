package com.crypto.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoPriceTest {

    @Test
    void testCryptoPriceConstructorAndGetters() {
        LocalDateTime lastUpdated = LocalDateTime.now();

        // Test du constructeur
        CryptoPrice cryptoPrice = new CryptoPrice(
                "1", "Bitcoin", "BTC", 50000.0, 1000000.0, 1000000000.0, lastUpdated
        );

        assertEquals("1", cryptoPrice.getId());
        assertEquals("Bitcoin", cryptoPrice.getName());
        assertEquals("BTC", cryptoPrice.getSymbol());
        assertEquals(50000.0, cryptoPrice.getPrice());
        assertEquals(1000000.0, cryptoPrice.getVolume24h());
        assertEquals(1000000000.0, cryptoPrice.getMarketCap());
        assertEquals(lastUpdated, cryptoPrice.getLastUpdated());
    }

    @Test
    void testCryptoPriceSetters() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        CryptoPrice cryptoPrice = new CryptoPrice();

        // Test des setters
        cryptoPrice.setId("2");
        cryptoPrice.setName("Ethereum");
        cryptoPrice.setSymbol("ETH");
        cryptoPrice.setPrice(3000.0);
        cryptoPrice.setVolume24h(500000.0);
        cryptoPrice.setMarketCap(500000000.0);
        cryptoPrice.setLastUpdated(lastUpdated);

        assertEquals("2", cryptoPrice.getId());
        assertEquals("Ethereum", cryptoPrice.getName());
        assertEquals("ETH", cryptoPrice.getSymbol());
        assertEquals(3000.0, cryptoPrice.getPrice());
        assertEquals(500000.0, cryptoPrice.getVolume24h());
        assertEquals(500000000.0, cryptoPrice.getMarketCap());
        assertEquals(lastUpdated, cryptoPrice.getLastUpdated());
    }
}
