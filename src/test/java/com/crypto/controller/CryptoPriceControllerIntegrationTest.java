package com.crypto.controller;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class CryptoPriceControllerIntegrationTest {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    private CryptoPriceController cryptoPriceController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cryptoPriceController).build();

        // Nettoyage et initialisation des données dans la base de données
        cryptoPriceRepository.deleteAll();
        cryptoPriceRepository.save(new CryptoPrice("1", "Bitcoin", "BTC", 50000.0, 1000000.0, 1000000000.0, LocalDateTime.now()));
        cryptoPriceRepository.save(new CryptoPrice("2", "Ethereum", "ETH", 3000.0, 500000.0, 500000000.0, LocalDateTime.now()));
        // Ajoutez d'autres éléments si nécessaire pour un total de 10
        cryptoPriceRepository.save(new CryptoPrice("3", "Ripple", "XRP", 1.0, 1000000.0, 1000000000.0, LocalDateTime.now()));
        cryptoPriceRepository.save(new CryptoPrice("4", "Litecoin", "LTC", 200.0, 500000.0, 500000000.0, LocalDateTime.now()));
        // Continuez à ajouter jusqu'à 10 éléments
    }

    @Test
    void getCryptos_ShouldReturnTop10Cryptos() throws Exception {
        // Test d'intégration : appeler le contrôleur via MockMvc et vérifier les réponses HTTP
        mockMvc.perform(get("/api/cryptos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))  // Attendu : 10 éléments
                .andExpect(jsonPath("$[0].name").value("Bitcoin"))
                .andExpect(jsonPath("$[1].name").value("Ethereum"))
                .andExpect(jsonPath("$[2].name").value("Ripple"))
                .andExpect(jsonPath("$[3].name").value("Litecoin"));
    }
}
