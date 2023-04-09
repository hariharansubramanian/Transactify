package dev.hari.playground.transactify.service.impl.crypto;

import dev.hari.playground.transactify.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.transactify.service.ConversionService;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Cryptocurrency specific implementation of {@link ConversionService}
 */
public class CryptoConversionService implements ConversionService {
    @Override
    public BigDecimal convert(BigDecimal amount, Currency sourceCurrency, Currency destinationCurrency) throws ExchangeRatesFetchException {
        throw new NotImplementedException("Crypto convert not implemented yet");
    }
}
