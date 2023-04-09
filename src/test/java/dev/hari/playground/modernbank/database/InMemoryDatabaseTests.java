package dev.hari.playground.modernbank.database;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;
import dev.hari.playground.modernbank.model.TransactionType;
import dev.hari.playground.modernbank.model.builders.AccountBuilder;
import dev.hari.playground.modernbank.model.builders.TransactionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InMemoryDatabaseTests {

    private InMemoryDatabase inMemoryDatabase;

    @BeforeEach
    public void setUp() {
        inMemoryDatabase = InMemoryDatabase.getInstance();
        inMemoryDatabase.reset(); // singleton instance, reset the database
    }

    @Test
    void saveAccountAndGet() {
        // Create an account
        var account = new AccountBuilder()
                .isActive(true)
                .withCurrency(Currency.getInstance("USD"))
                .withBalance(BigDecimal.valueOf(100))
                .build();

        // Save the account
        Account savedAccount = inMemoryDatabase.saveAccount(account);

        // Assert the account
        assertNotNull(savedAccount);
        assertEquals(1, savedAccount.id);
        assertTrue(account.isBalanceEquals(savedAccount.balance));
        assertEquals(account.currency, savedAccount.currency);

        // Assert the database
        Map<Long, Account> accountMap = inMemoryDatabase.getAccountMap();
        assertEquals(1, accountMap.size());
        assertEquals(savedAccount, accountMap.get(1L));
    }

    @Test
    void saveTransaction() {
        //  Create an account
        Account account = new AccountBuilder()
                .isActive(true)
                .withCurrency(Currency.getInstance("USD"))
                .withBalance(BigDecimal.valueOf(100))
                .build();

        // Save the account
        inMemoryDatabase.saveAccount(account);

        // Create a transaction
        Transaction transaction = new TransactionBuilder()
                        .withAccount(account)
                        .withAmount(BigDecimal.valueOf(50))
                        .withType(TransactionType.DEBIT)
                        .withCreatedAt(ZonedDateTime.now())
                        .build();

        // Save the transaction
        Transaction savedTransaction = inMemoryDatabase.saveTransaction(transaction);

        // Assert the transaction
        assertNotNull(savedTransaction);
        assertEquals(1, savedTransaction.id);
        assertEquals(0, transaction.getAmount().compareTo(savedTransaction.getAmount()));
        assertEquals(transaction.type, savedTransaction.type);

        // Assert the database
        Account savedAccount = inMemoryDatabase.getAccountMap().get(1L);
        assertEquals(1, savedAccount.transactions.size());
        assertEquals(savedTransaction, savedAccount.transactions.get(0));
    }
}