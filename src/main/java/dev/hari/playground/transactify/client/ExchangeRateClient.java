package dev.hari.playground.transactify.client;

import dev.hari.playground.transactify.dto.processPayment.GetExchangeRatesResponse;
import dev.hari.playground.transactify.exception.classes.ExchangeRatesFetchException;

import java.util.Currency;

public interface ExchangeRateClient {
    GetExchangeRatesResponse getExchangeRates(Currency baseCurrency) throws ExchangeRatesFetchException;
}
