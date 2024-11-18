package com.crypto.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "cryptoprices")
public class CryptoPrice {
    @Id
    private String id;
    private String name;
    private String symbol;
    private Double price;
    private Double volume24h;
    private Double marketCap;
    private LocalDateTime lastUpdated;
}