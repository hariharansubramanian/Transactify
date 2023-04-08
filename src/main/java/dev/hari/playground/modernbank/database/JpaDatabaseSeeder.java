package dev.hari.playground.modernbank.database;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.TransactionType;
import dev.hari.playground.modernbank.model.builders.AccountBuilder;
import dev.hari.playground.modernbank.model.builders.TransactionBuilder;
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
                .withTransaction(new TransactionBuilder()
                        .withAmount(BigDecimal.valueOf(100.22))
                        .withCurrency(Currency.getInstance("USD"))
                        .withType(TransactionType.CREDIT)
                        .build())
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
