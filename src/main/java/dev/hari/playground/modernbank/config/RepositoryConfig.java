package dev.hari.playground.modernbank.config;

import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.repository.TransactionRepository;
import dev.hari.playground.modernbank.repository.impl.InMemoryAccountRepository;
import dev.hari.playground.modernbank.repository.impl.InMemoryTransactionRepository;
import dev.hari.playground.modernbank.repository.impl.JpaAccountRepository;
import dev.hari.playground.modernbank.repository.impl.JpaTransactionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    @ConditionalOnProperty(name = {"jpa.enabled"}, havingValue = "true")
    public AccountRepository jpaAccountRepository(JpaAccountRepository jpaAccountRepository) {
        return jpaAccountRepository;
    }

    @Bean
    @ConditionalOnProperty(name = {"jpa.enabled"}, havingValue = "true")
    public TransactionRepository jpaTransactionRepository(JpaTransactionRepository jpaTransactionRepository) {
        return jpaTransactionRepository;
    }

    @Bean
    @ConditionalOnProperty(name = {"inMemoryDb.enabled"}, havingValue = "true")
    public AccountRepository inMemoryAccountRepository() {
        return new InMemoryAccountRepository();
    }

    @Bean
    @ConditionalOnProperty(name = {"inMemoryDb.enabled"}, havingValue = "true")
    public TransactionRepository inMemoryTransactionRepository() {
        return new InMemoryTransactionRepository();
    }
}
