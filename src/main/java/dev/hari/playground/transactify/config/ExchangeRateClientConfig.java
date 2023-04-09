package dev.hari.playground.transactify.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hari.playground.transactify.client.ExchangeRateClient;
import dev.hari.playground.transactify.client.impl.ApiExchangeRateClient;
import dev.hari.playground.transactify.client.impl.FileExchangeRateClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * Configure ExchangeRatesClient based on the application.properties
 * ApiExchangeRateClient is used if apiExchange.enabled is true
 * FileExchangeRatesClient is used if fileExchange.enabled is true
 */
@Configuration
public class ExchangeRateClientConfig {

    @Bean
    @Primary
    @ConditionalOnProperty(name = {"apiExchange.enabled"}, havingValue = "true")
    @ConditionalOnExpression("${fileExchange.enabled:false} == false")
    public ExchangeRateClient apiExchangeRatesClient(RestTemplate restTemplate) {
        return new ApiExchangeRateClient(restTemplate);
    }

    @Bean
    @Primary
    @ConditionalOnProperty(name = {"fileExchange.enabled"}, havingValue = "true")
    public ExchangeRateClient fileExchangeRatesClient(ObjectMapper objectMapper) {
        return new FileExchangeRateClient(objectMapper);
    }
}