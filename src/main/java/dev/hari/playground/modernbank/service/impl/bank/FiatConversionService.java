package dev.hari.playground.modernbank.service.impl.bank;

import dev.hari.playground.modernbank.client.ExchangeRatesClient;
import dev.hari.playground.modernbank.exception.ExchangeRatesFetchException;
import dev.hari.playground.modernbank.service.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Fiat Currency specific implementations of {@link ConversionService} behaviors
 */
public class FiatConversionService implements ConversionService {

    private final ExchangeRatesClient conversionApiClient;

    public FiatConversionService(ExchangeRatesClient conversionApiClient) {
        this.conversionApiClient = conversionApiClient;
    }

    @Override
    public BigDecimal convert(BigDecimal amount, Currency sourceCurrency, Currency destinationCurrency) throws ExchangeRatesFetchException {
        // Do nothing if source and destination currencies are same
        if (sourceCurrency.equals(destinationCurrency)) {
            return amount;
        }

        // Get the exchange rates
        var response = conversionApiClient.getExchangeRates(sourceCurrency);

        // Get the conversion rate for destination currency
        var conversionRate = response.rates.get(destinationCurrency.getCurrencyCode());
        if (conversionRate == null) {
            throw new ExchangeRatesFetchException(String.format("No conversion rate found for %s", destinationCurrency.getCurrencyCode()));
        }

        // Return the converted amount
        return amount.multiply(conversionRate);
    }
}
