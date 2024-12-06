package com.crypto.repository;

import com.crypto.model.CryptoPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface CryptoPriceRepository extends MongoRepository<CryptoPrice, String> {
    //CryptoPrice findBySymbol(String symbol);
    List<CryptoPrice> findTop10ByOrderByIdAsc(); // Cette méthode récupère les 10 premiers éléments triés par ID
}