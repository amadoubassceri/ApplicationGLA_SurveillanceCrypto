package com.crypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CoinCapServiceTest {

    private CoinCapService coinCapService;

    @Value("${coincap.api.url}")
    private String apiUrl;

    @Value("${coincap.api.limit}")
    private int limit;

    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = new TestRestTemplate();
        coinCapService = new CoinCapService(restTemplate.getRestTemplate());
    }

    @Test
    void getTopCryptos_ShouldReturnData() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(apiUrl + "/assets?limit=" + limit, JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}