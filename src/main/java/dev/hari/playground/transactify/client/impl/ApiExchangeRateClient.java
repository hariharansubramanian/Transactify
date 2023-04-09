package dev.hari.playground.transactify.client.impl;

import dev.hari.playground.transactify.client.ExchangeRateClient;
import dev.hari.playground.transactify.dto.processPayment.GetExchangeRatesResponse;
import dev.hari.playground.transactify.exception.classes.ExchangeRatesFetchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Currency;

/**
 * Client for fetching exchange rates from external API
 */
@Component
public class ApiExchangeRateClient implements ExchangeRateClient {
    private final RestTemplate restTemplate;
    private static final String API_PATH = "https://open.er-api.com/v6/latest/"; // TODO: Move to config

    public ApiExchangeRateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches exchange rates from external API
     *
     * @param baseCurrency base currency
     * @return exchange rates of currencies against base currency
     * @throws ExchangeRatesFetchException if failed to fetch exchange rates
     */
    public GetExchangeRatesResponse getExchangeRates(Currency baseCurrency) throws ExchangeRatesFetchException {
        String apiUrl = API_PATH + baseCurrency.getCurrencyCode();

        ResponseEntity<GetExchangeRatesResponse> response = restTemplate.getForEntity(apiUrl, GetExchangeRatesResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // TODO: Cache this response
            return response.getBody();
        } else {
            throw new ExchangeRatesFetchException(String.format("Failed to fetch exchange rates for %s", baseCurrency.getCurrencyCode()));
        }
    }
}
