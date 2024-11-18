package com.crypto.repository;

import com.crypto.model.CryptoPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CryptoPriceRepository extends MongoRepository<CryptoPrice, String> {
    CryptoPrice findBySymbol(String symbol);
}