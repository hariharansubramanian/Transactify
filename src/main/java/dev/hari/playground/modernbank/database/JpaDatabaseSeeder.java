package dev.hari.playground.modernbank.database;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.AccountBuilder;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

@Component
public class JpaDatabaseSeeder implements CommandLineRunner {
    private final AccountRepository jpaAccountRepository;

    public JpaDatabaseSeeder(AccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Account activeAmericanAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000.45))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        Account activeIndianAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(81824.60))
                .withCurrency(Currency.getInstance("INR"))
                .build();

        Account inactiveNorwegianAccount = new AccountBuilder()
                .isActive(false)
                .withCurrency(Currency.getInstance("NOK"))
                .withBalance(BigDecimal.valueOf(1000000.24))
                .build();

        jpaAccountRepository.save(activeAmericanAccount);
        jpaAccountRepository.save(activeIndianAccount);
        jpaAccountRepository.save(inactiveNorwegianAccount);
    }
}
