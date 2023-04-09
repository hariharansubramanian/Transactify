package dev.hari.playground.modernbank.config;

import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.repository.TransactionRepository;
import dev.hari.playground.modernbank.repository.impl.InMemoryAccountRepository;
import dev.hari.playground.modernbank.repository.impl.InMemoryTransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }

    @Bean
    public TransactionRepository transactionRepository() {
        return new InMemoryTransactionRepository();
    }
}
