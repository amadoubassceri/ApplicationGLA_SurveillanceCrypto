package com.crypto.repository;

import com.crypto.model.CryptoPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class CryptoPriceRepositoryTest {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @BeforeEach
    void setUp() {
        // Suppression de toutes les données avant chaque test
        cryptoPriceRepository.deleteAll();
    }

    @Test
    void findTop10ByOrderByIdAsc_ShouldReturnTop10Cryptos() {
        // Préparation des données de test
        for (int i = 1; i <= 15; i++) {
            CryptoPrice crypto = new CryptoPrice();
            crypto.setName("Crypto " + i);
            crypto.setSymbol("CRP" + i);
            crypto.setPrice(1000.0 * i);
            crypto.setVolume24h(10000.0 * i);
            crypto.setMarketCap(1000000.0 * i);
            crypto.setLastUpdated(LocalDateTime.now());
            cryptoPriceRepository.save(crypto);
        }

        // Vérification du nombre total d'éléments insérés
        System.out.println("Nombre total d'éléments insérés : " + cryptoPriceRepository.count());

        // Exécution
        List<CryptoPrice> result = cryptoPriceRepository.findTop10ByOrderByIdAsc();

        // Vérification
        assertEquals(10, result.size());
        assertEquals("Crypto 1", result.get(0).getName());
        assertEquals("Crypto 10", result.get(9).getName());
    }

    @Test
    void save_ShouldPersistCryptoPrice() {
        // Préparation
        CryptoPrice crypto = new CryptoPrice();
        crypto.setName("Bitcoin");
        crypto.setSymbol("BTC");
        crypto.setPrice(50000.0);
        crypto.setVolume24h(1000000.0);
        crypto.setMarketCap(1000000000.0);
        crypto.setLastUpdated(LocalDateTime.now());

        // Exécution
        CryptoPrice savedCrypto = cryptoPriceRepository.save(crypto);

        // Vérifications
        assertNotNull(savedCrypto.getId());
        assertEquals("Bitcoin", savedCrypto.getName());
        assertEquals("BTC", savedCrypto.getSymbol());

        // Vérification de récupération
        CryptoPrice retrieved = cryptoPriceRepository.findById(savedCrypto.getId()).orElse(null);
        assertNotNull(retrieved);
        assertEquals(savedCrypto.getName(), retrieved.getName());
    }
}
