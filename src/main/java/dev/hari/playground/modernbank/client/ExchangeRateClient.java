package dev.hari.playground.modernbank.client;

import dev.hari.playground.modernbank.dto.processPayment.GetExchangeRatesResponse;
import dev.hari.playground.modernbank.exception.classes.ExchangeRatesFetchException;

import java.util.Currency;

public interface ExchangeRateClient {
    GetExchangeRatesResponse getExchangeRates(Currency baseCurrency) throws ExchangeRatesFetchException;
}
