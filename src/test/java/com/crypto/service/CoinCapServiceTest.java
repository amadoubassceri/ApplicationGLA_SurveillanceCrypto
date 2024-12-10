package com.crypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CoinCapServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CoinCapService coinCapService; // Assurez-vous que CoinCapService est bien importé

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Initialisation manuelle des valeurs des propriétés @Value
        ReflectionTestUtils.setField(coinCapService, "apiUrl", "https://api.coincap.io/v2");
        ReflectionTestUtils.setField(coinCapService, "limit", 10);
    }

    @Test
    void getTopCryptos_ShouldReturnData() throws Exception {
        // Réponse simulée de l'API
        String mockResponse = "{\"data\": [{\"id\": \"bitcoin\",\"symbol\": \"BTC\",\"name\": \"Bitcoin\"}]}";
        JsonNode mockJsonNode = objectMapper.readTree(mockResponse);

        // Simuler le comportement de RestTemplate
        lenient().when(restTemplate.getForObject(anyString(), eq(JsonNode.class)))
                .thenReturn(mockJsonNode);

        // Appel de la méthode et validation du résultat
        JsonNode result = coinCapService.getTopCryptos();

        assertNotNull(result);
        assertTrue(result.has("data"));
        assertEquals("Bitcoin", result.get("data").get(0).get("name").asText());
    }

    @Test
    void getTopCryptos_WhenApiError_ShouldThrowException() {
        // Simuler une exception lors de l'appel RestTemplate
        lenient().when(restTemplate.getForObject(anyString(), eq(JsonNode.class)))
                .thenThrow(new RuntimeException("API Error"));

        // Vérifier que l'exception RuntimeException est bien lancée
        assertThrows(RuntimeException.class, () -> coinCapService.getTopCryptos());
    }
}
