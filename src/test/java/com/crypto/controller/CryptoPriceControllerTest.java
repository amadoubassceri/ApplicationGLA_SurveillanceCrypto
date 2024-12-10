package com.crypto.controller;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(CryptoPriceController.class)
public class CryptoPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoPriceRepository cryptoPriceRepository;

    private List<CryptoPrice> cryptoPrices;

    @BeforeEach
    void setUp() {
        CryptoPrice bitcoin = new CryptoPrice();
        bitcoin.setId("1");
        bitcoin.setName("Bitcoin");
        bitcoin.setSymbol("BTC");
        bitcoin.setPrice(50000.0);
        bitcoin.setVolume24h(1000000.0);
        bitcoin.setMarketCap(1000000000.0);
        bitcoin.setLastUpdated(LocalDateTime.now());

        CryptoPrice ethereum = new CryptoPrice();
        ethereum.setId("2");
        ethereum.setName("Ethereum");
        ethereum.setSymbol("ETH");
        ethereum.setPrice(3000.0);
        ethereum.setVolume24h(500000.0);
        ethereum.setMarketCap(500000000.0);
        ethereum.setLastUpdated(LocalDateTime.now());

        cryptoPrices = Arrays.asList(bitcoin, ethereum);
    }

    @Test
    void getCryptos_ShouldReturnTop10Cryptos() throws Exception {
        when(cryptoPriceRepository.findTop10ByOrderByIdAsc()).thenReturn(cryptoPrices);

        mockMvc.perform(get("/api/cryptos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Bitcoin"))
                .andExpect(jsonPath("$[1].name").value("Ethereum"));
    }
}