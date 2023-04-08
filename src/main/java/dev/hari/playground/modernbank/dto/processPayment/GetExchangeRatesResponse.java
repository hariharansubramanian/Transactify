package dev.hari.playground.modernbank.dto.processPayment;

import java.math.BigDecimal;
import java.util.Map;

/**
 * JSON response from https://api.exchangeratesapi.io/latest
 */
public class GetExchangeRatesResponse {
    public Map<String, BigDecimal> rates;
}


