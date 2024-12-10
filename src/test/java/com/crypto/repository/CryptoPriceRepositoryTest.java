package com.crypto.repository;

import com.crypto.model.CryptoPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
public class CryptoPriceRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.6");

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setUp() {
        cryptoPriceRepository.deleteAll();
    }

    @Test
    void findTop10ByOrderByIdAsc_ShouldReturnTop10Cryptos() {
        // Prepare test data
        for (int i = 1; i <= 15; i++) {
            CryptoPrice crypto = new CryptoPrice();
            crypto.setId(String.valueOf(i));
            crypto.setName("Crypto " + i);
            crypto.setSymbol("CRP" + i);
            crypto.setPrice(1000.0 * i);
            crypto.setVolume24h(10000.0 * i);
            crypto.setMarketCap(1000000.0 * i);
            crypto.setLastUpdated(LocalDateTime.now());
            cryptoPriceRepository.save(crypto);
        }

        // Execute
        List<CryptoPrice> result = cryptoPriceRepository.findTop10ByOrderByIdAsc();

        // Verify
        assertEquals(10, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("10", result.get(9).getId());
    }

    @Test
    void save_ShouldPersistCryptoPrice() {
        // Prepare
        CryptoPrice crypto = new CryptoPrice();
        crypto.setName("Bitcoin");
        crypto.setSymbol("BTC");
        crypto.setPrice(50000.0);
        crypto.setVolume24h(1000000.0);
        crypto.setMarketCap(1000000000.0);
        crypto.setLastUpdated(LocalDateTime.now());

        // Execute
        CryptoPrice savedCrypto = cryptoPriceRepository.save(crypto);

        // Verify
        assertNotNull(savedCrypto.getId());
        assertEquals("Bitcoin", savedCrypto.getName());
        assertEquals("BTC", savedCrypto.getSymbol());

        // Verify it can be retrieved
        CryptoPrice retrieved = cryptoPriceRepository.findById(savedCrypto.getId()).orElse(null);
        assertNotNull(retrieved);
        assertEquals(savedCrypto.getName(), retrieved.getName());
    }
}