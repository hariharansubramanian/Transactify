package dev.hari.playground.modernbank.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hari.playground.modernbank.client.ExchangeRateClient;
import dev.hari.playground.modernbank.dto.processPayment.GetExchangeRatesResponse;
import dev.hari.playground.modernbank.exception.classes.ExchangeRatesFetchException;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

/**
 * Mock client that fetches exchange rates from a resource file 'exchange_rates.json'
 */
@Component
public class FileExchangeRateClient implements ExchangeRateClient {

    private final ObjectMapper objectMapper;

    public FileExchangeRateClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Fetches exchange rates from a resource file 'exchange_rates.json'
     *
     * @param baseCurrency base currency
     * @return exchange rates of currencies against base currency
     * @throws ExchangeRatesFetchException if failed to fetch exchange rates
     */
    @Override
    public GetExchangeRatesResponse getExchangeRates(Currency baseCurrency) throws ExchangeRatesFetchException {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("exchange_rates.json");
            JsonNode rootNode = objectMapper.readTree(inputStream);
            JsonNode ratesNode = rootNode.path(baseCurrency.getCurrencyCode()).path("rates");
            Map<String, BigDecimal> ratesMap = objectMapper.convertValue(ratesNode, new TypeReference<>() {
            });

            return new GetExchangeRatesResponse(ratesMap);
        } catch (Exception e) {
            throw new ExchangeRatesFetchException("Failed to fetch exchange rates from file");
        }
    }
}
