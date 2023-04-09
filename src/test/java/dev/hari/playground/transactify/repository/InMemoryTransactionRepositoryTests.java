package dev.hari.playground.transactify.repository;

import dev.hari.playground.transactify.database.InMemoryDatabase;
import dev.hari.playground.transactify.model.Transaction;
import dev.hari.playground.transactify.model.builders.AccountBuilder;
import dev.hari.playground.transactify.model.builders.TransactionBuilder;
import dev.hari.playground.transactify.repository.impl.inMemory.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class InMemoryTransactionRepositoryTests {
    private TransactionRepository transactionRepository;
    private InMemoryDatabase inMemoryDatabase;

    @BeforeEach
    public void setUp() {
        inMemoryDatabase = InMemoryDatabase.getInstance();
        inMemoryDatabase.reset();
        transactionRepository = new InMemoryTransactionRepository();
    }

    @Test
    void findAllByAccountId() {
        // Create an account
        var account = new AccountBuilder()
                .isActive(true)
                .withCurrency(Currency.getInstance("USD"))
                .withBalance(BigDecimal.valueOf(100.0))
                .build();
        account = inMemoryDatabase.saveAccount(account);

        // Create a transaction
        var transaction = new TransactionBuilder()
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(50.0))
                .withCreatedAt(ZonedDateTime.now())
                .build();
        inMemoryDatabase.saveTransaction(transaction);

        // Find the transaction
        Pageable pageable = PageRequest.of(0, 10);
        List<Transaction> transactions = transactionRepository.findAllByAccountId(account.id, pageable);
        Transaction transactionFromDb = transactions.get(0);

        assertEquals(1, transactions.size());
        assertEquals(0, transaction.getAmount().compareTo(transactionFromDb.getAmount()));
        assertEquals(transaction.getCreatedAt(), transactionFromDb.getCreatedAt());
    }

    @Test
    void findAllByAccountId_ReturnsEmpty_WhenNoAccount() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Transaction> transactions = transactionRepository.findAllByAccountId(9999, pageable);

        assertTrue(transactions.isEmpty());
    }

    @Test
    void saveTransaction() {
        // Create an account
        var account = new AccountBuilder()
                .isActive(true)
                .withCurrency(Currency.getInstance("USD"))
                .withBalance(BigDecimal.valueOf(100.0))
                .build();
        account = inMemoryDatabase.saveAccount(account);

        // Create a transaction
        var transaction = new TransactionBuilder()
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(50.0))
                .withCreatedAt(ZonedDateTime.now())
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Assert the transaction
        assertEquals(0, transaction.getAmount().compareTo(savedTransaction.getAmount()));
        assertEquals(transaction.getCreatedAt(), savedTransaction.getCreatedAt());
    }
}