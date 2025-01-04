package com.cryptocurrency.data.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.cryptocurrency.data.model.CryptoPriceHistory;
import com.cryptocurrency.data.model.CryptoCurrency;
import com.cryptocurrency.data.repository.CryptoCurrencyRepository;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The service for data collection.
 * Author: Amadou BASS
 */
@Service
public class DataCollectionService {

    /**
     * The repository for CryptoCurrency objects.
     * */
    @Autowired
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    /**
     * A map of all the CryptoPriceHistory objects in the database.
     * Each key is the name of a cryptocurrency, and the value is a list of
     * CryptoPriceHistory objects for that currency.
     * */
    private static Map<String, List<CryptoPriceHistory>> cryptoPriceHistoryMap = new HashMap<>();

    /**
     * The logger for the service.
     * */
    private static final Logger logger = LoggerFactory.getLogger(DataCollectionService.class);

    /**
     * The limit for the number of lines in the table.
     * */
    private static final int LIMIT_LINE = 20;

    /**
     * The bearer token for the CoinCap API.
     * */
    @Value("${api.coincap.bearer-token}")
    private String bearerToken;

    /**
     * The path to the data file.
     * */
    private static final String DATA_FILE_NAME = "crypto_price_history.json";

    /**
     * Constructs a new instance of the DataCollectionService class.
     *
     * @param cryptoCurrencyRepository The repository for CryptoCurrency objects.
     */
    public DataCollectionService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    /**
     * Chargement des données du fichier JSON au démarrage.
     */
    @PostConstruct
    public void loadCryptoPriceHistory() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(DATA_FILE_NAME)).getFile());

            if (file.exists()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                TypeReference<Map<String, List<CryptoPriceHistory>>> typeReference = new TypeReference<>() {};
                cryptoPriceHistoryMap = objectMapper.readValue(file, typeReference);
                System.out.println("Les données ont été chargées avec succès.");
            } else {
                System.out.println("Fichier non trouvé, un fichier vide sera créé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the crypto price history data to a file when the application is stopped.
     * The data is saved in JSON format to a file named "crypto_price_history.json"
     * in the "src/main/resources" directory.
     * */
    @PreDestroy
    public void saveCryptoPriceHistory() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            Path path = Paths.get("src/main/resources/" + DATA_FILE_NAME);

            objectMapper.writeValue(path.toFile(), cryptoPriceHistoryMap);
            System.out.println("Les données ont été sauvegardées avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new entry for each list of cryptocurrencies to the price history table in the database.
     * Only existing cryptocurrencies in the database are added.
     *
     * @param cryptoCurrencyList a list of CryptoCurrency objects
     */
    public void addCryptoForPriceHistory(List<CryptoCurrency> cryptoCurrencyList) {
        System.out.println("start adding cryptoCurrency for price history dans DataCollectionService");
        for (CryptoCurrency crypto : cryptoCurrencyList) {
            String name = crypto.getName();
            LocalDateTime timestamp = LocalDateTime.now();
            Double price = crypto.getPrice();

            cryptoPriceHistoryMap.putIfAbsent(name, new ArrayList<>());
            CryptoPriceHistory cryptoPriceHistory = new CryptoPriceHistory(timestamp, price);
            cryptoPriceHistoryMap.get(name).add(cryptoPriceHistory);
            System.out.println("Ajout dans la boucle is OKay for price history dans DataCollectionService");
        }
        System.out.println("Collect for price history is done ...");
    }


    /**
     * This method is scheduled to run every 60 seconds and collects market data from the CoinCap API.
     * The collected data is then saved to the database.
     */
    @Scheduled(fixedRate = 30000)
    public void collectMarketData() {
        logger.info("Starting data collection ......");

        String apiUrl = "https://api.coincap.io/v2/assets";

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .header("Authorization", bearerToken)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();
                List<CryptoCurrency> cryptoCurrencyList = parseMarketData(responseBody);

                enforceTableLimitBeforeInsert();
                cryptoCurrencyRepository.saveAll(cryptoCurrencyList);

                System.out.println("Ajout des historiques de prix ...");
                addCryptoForPriceHistory(cryptoCurrencyList);
                System.out.println("Ajout terminé.");

                logger.info("20 new lines inserée dans la table CryptoCurrency ...........");
            }

        } catch (Exception e) {
            logger.error("An error occurred during data collection: ", e);
        }

        logger.info("Fin de la collection de donnée");
    }

    /**
     * Parses the market data from the CoinCap API response.
     *
     * @param jsonResponse The JSON response from the CoinCap API.
     * @return A list of CryptoCurrency objects parsed from the API response.
     */
    private List<CryptoCurrency> parseMarketData(String jsonResponse) {
        List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = rootNode.path("data");

            int count = 0;
            if (dataNode.isArray()) {
                for (JsonNode node : dataNode) {
                    if (count >= LIMIT_LINE) break;

                    String name = node.path("name").asText();
                    String symbol = node.path("symbol").asText();
                    int rank = node.path("rank").asInt();
                    double supply = node.path("supply").asDouble();
                    double maxSupply = node.path("maxSupply").asDouble();
                    double market = node.path("marketCapUsd").asDouble();
                    double volume = node.path("volumeUsd24Hr").asDouble();
                    double price = node.path("priceUsd").asDouble();
                    double change = node.path("changePercent24Hr").asDouble();
                    double vwap = node.path("vwap24Hr").asDouble();;

                    CryptoCurrency cryptocurrency = new CryptoCurrency();
                    cryptocurrency.setName(name);
                    cryptocurrency.setSymbol(symbol);
                    cryptocurrency.setRank(rank);
                    cryptocurrency.setSupply(supply);
                    cryptocurrency.setMaxSupply(maxSupply);
                    cryptocurrency.setMarket(market);
                    cryptocurrency.setVolume(volume);
                    cryptocurrency.setPrice(price);
                    cryptocurrency.setChange(change);
                    cryptocurrency.setVwap(vwap);
                    cryptocurrency.setTimestamp(LocalDateTime.now());

                    cryptoCurrencyList.add(cryptocurrency);
                    count++;
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors du parsing des données: ", e);
        }
        return cryptoCurrencyList;
    }

    /**
     * Enforces the limit on the number of rows in the CryptoCurrency table.
     * Deletes the oldest rows if the total exceeds the limit BEFORE inserting new data.
     */
    private void enforceTableLimitBeforeInsert() {
        logger.info("supprimer les anciennes 20 lignes");
        List<CryptoCurrency> cryptoCurrencyList = cryptoCurrencyRepository.findAll();
        if (cryptoCurrencyList.size() == LIMIT_LINE) {
            System.out.println(cryptoCurrencyList.size() + " taille");
            logger.info("{} taille ", cryptoCurrencyList.size());
            cryptoCurrencyRepository.deleteAll();
        }
    }

    /**
     * Returns a map of all the CryptoPriceHistory objects in the database.
     * Each key is the name of a cryptocurrency, and the value is a list of
     * CryptoPriceHistory objects for that currency.
     *
     * @return A map of all the CryptoPriceHistory objects in the database.
     */
    public static Map<String, List<CryptoPriceHistory>> getCryptoPriceHistoryMap() {
        System.out.println("get is okay for price history");
        return cryptoPriceHistoryMap;
    }
}
