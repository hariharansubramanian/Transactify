package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.exception.ExchangeRatesFetchException;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Service contract for conversion of financial entities (Currency, Crypto etc)
 */
public interface ConversionService {
    /**
     * Converts the given amount from source to destination
     *
     * @param amount              - amount to be converted
     * @param sourceCurrency      - currency from which the amount should be converted
     * @param destinationCurrency - currency to which the amount should be converted
     * @return converted amount
     */
    BigDecimal convert(BigDecimal amount, Currency sourceCurrency, Currency destinationCurrency) throws ExchangeRatesFetchException;
}
