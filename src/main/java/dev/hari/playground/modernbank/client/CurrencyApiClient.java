package dev.hari.playground.modernbank.client;

import dev.hari.playground.modernbank.dto.processPayment.GetExchangeRatesResponse;
import dev.hari.playground.modernbank.exception.ExchangeRatesFetchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Currency;

/**
 * Client for fetching exchange rates from external API
 */
@Component
public class CurrencyApiClient {
    private final RestTemplate restTemplate;
    private static final String API_PATH = "https://open.er-api.com/v6/latest/"; // TODO: Move to config

    public CurrencyApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
