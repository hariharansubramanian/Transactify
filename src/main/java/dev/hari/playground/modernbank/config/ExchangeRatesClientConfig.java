package dev.hari.playground.modernbank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hari.playground.modernbank.client.ExchangeRatesClient;
import dev.hari.playground.modernbank.client.impl.FileExchangeRatesClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for exchange rates client
 */
@Configuration
public class ExchangeRatesClientConfig {

    @Bean
    public ExchangeRatesClient conversionApiClient() {
        return new FileExchangeRatesClient(new ObjectMapper());
    }
}
