package com.crypto.controller;

import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptoPriceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CryptoPriceController {

    private final CryptoPriceRepository cryptoPriceRepository;

    public CryptoPriceController(CryptoPriceRepository cryptoPriceRepository) {
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    @GetMapping("/api/cryptos")
    public List<CryptoPrice> getCryptos() {
       // return cryptoPriceRepository.findAll();  // Récupère toutes les cryptomonnaies de MongoDB
        return cryptoPriceRepository.findTop10ByOrderByIdAsc();  // Limite les résultats à 10
    }
}
