package dev.hari.playground.modernbank.database;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.builders.AccountBuilder;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class JpaDatabaseSeeder implements CommandLineRunner {
    private final AccountRepository jpaAccountRepository;

    public JpaDatabaseSeeder(AccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public void run(String... args) {

        // Create accounts with random transactions, keeping the balance positive
        Account activeAmericanAccount = new AccountBuilder()
                .isActive(true)
                .withCurrency(Currency.getInstance("USD"))
                .withRandomTransactions(100, 1, 500)
                .build();

        Account activeIndianAccount = new AccountBuilder()
                .isActive(true)
                .withCurrency(Currency.getInstance("INR"))
                .withRandomTransactions(100, 81.84, 40920.25)
                .build();

        Account inactiveNorwegianAccount = new AccountBuilder()
                .isActive(false)
                .withCurrency(Currency.getInstance("NOK"))
                .withRandomTransactions(100, 10.50, 5247.50)
                .build();

        jpaAccountRepository.save(activeAmericanAccount);
        jpaAccountRepository.save(activeIndianAccount);
        jpaAccountRepository.save(inactiveNorwegianAccount);
    }
}
