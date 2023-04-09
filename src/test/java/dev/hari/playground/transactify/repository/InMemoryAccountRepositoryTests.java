package dev.hari.playground.transactify.repository;

import dev.hari.playground.transactify.database.InMemoryDatabase;
import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.repository.impl.inMemory.InMemoryAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InMemoryAccountRepositoryTests {
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        inMemoryDatabase.reset();
        accountRepository = new InMemoryAccountRepository();
    }

    @Test
    void findAccountById() {
        // Create an account
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(100.0));

        // Save the account
        Account savedAccount = accountRepository.save(account);
        Account foundAccount = accountRepository.findAccountById(savedAccount.getId());

        // Assert the account
        assertEquals(savedAccount.getId(), foundAccount.getId());
        assertEquals(savedAccount.getBalance(), foundAccount.getBalance());
    }

    @Test
    void findAccountById_ReturnsNull_WhenNotFound() {
        Account foundAccount = accountRepository.findAccountById(9999);
        assertNull(foundAccount);
    }

    @Test
    void saveAccount() {
        // Create an account
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(200.0));

        // Save the account
        Account savedAccount = accountRepository.save(account);

        // Assert the account
        assertTrue(account.isBalanceEquals(savedAccount.getBalance()));
    }
}