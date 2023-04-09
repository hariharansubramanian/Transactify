package dev.hari.playground.modernbank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hari.playground.modernbank.client.ExchangeRateClient;
import dev.hari.playground.modernbank.client.impl.FileExchangeRateClient;
import dev.hari.playground.modernbank.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.modernbank.service.impl.bank.FiatConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FiatConversionTests {
    private ExchangeRateClient exchangeRateClient;
    private FiatConversionService fiatConversionService;

    @BeforeEach
    public void setUp() {
        exchangeRateClient = new FileExchangeRateClient(new ObjectMapper());
        fiatConversionService = new FiatConversionService(exchangeRateClient);
    }

    @Test
    void convert_ReturnsSameAmount_ForSameCurrency() throws ExchangeRatesFetchException {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency srcCurrency = Currency.getInstance("USD");
        Currency destCurrency = Currency.getInstance("USD");

        // Act
        var convertedAmount = fiatConversionService.convert(amount, srcCurrency, destCurrency);

        // Assert
        assertEquals(0, amount.compareTo(convertedAmount));
    }

    @Test
    void convert_ReturnsConvertedAmount_ForDifferentCurrency() throws ExchangeRatesFetchException {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency srcCurrency = Currency.getInstance("USD");
        Currency destCurrency = Currency.getInstance("INR");

        // Act
        var convertedAmount = fiatConversionService.convert(amount, srcCurrency, destCurrency);

        // Assert
        assertEquals(1, convertedAmount.compareTo(amount));
    }

    @Test
    void convert_ThrowsExchangeRatesFetchException_WhenGetExchangeRatesFails() throws ExchangeRatesFetchException {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(100);
        Currency srcCurrency = Currency.getInstance("USD");
        Currency destCurrency = Currency.getInstance("INR");

        // Mock behavior of exchangeRateClient
        exchangeRateClient =  mock(ExchangeRateClient.class);
        when(exchangeRateClient.getExchangeRates(srcCurrency)).thenThrow(new ExchangeRatesFetchException("Mocked exception"));
        fiatConversionService = new FiatConversionService(exchangeRateClient);

        // Act & Assert
        assertThrows(ExchangeRatesFetchException.class, () -> fiatConversionService.convert(amount, srcCurrency, destCurrency));
    }
}
