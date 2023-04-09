package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.getStatement.TransactionResult;
import dev.hari.playground.modernbank.exception.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.builders.AccountBuilder;
import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.service.impl.bank.BankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankAccountServiceTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * ----------------------- GetAccountBalance Tests -----------------------
     */
    @Test
    void GetBalance_ShouldThrowInvalidAccountException_WhenInvalidAccount() {
        // Arrange
        long invalidAccountId = 999;

        // Act & Assert
        assertThrows(InvalidAccountException.class, () -> accountService.getBalance(invalidAccountId));
    }

    @Test
    void GetBalance_ShouldReturnAccountBalance_WhenValidAccount() throws InvalidAccountException {
        // Arrange
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(account);

        // Act
        GetAccountBalanceResult result = accountService.getBalance(account.id);

        // Assert
        assertTrue(account.isBalanceEquals(result.balance));
    }

    /**
     * ----------------------- GetAccountStatement Tests -----------------------
     */

    @Test
    void GetStatement_ShouldThrowInvalidAccountException_WhenInvalidAccount() {
        // Arrange
        long invalidAccountId = 999;
        int transactionCount = 10;

        // Act & Assert
        assertThrows(InvalidAccountException.class, () -> accountService.getStatement(invalidAccountId, transactionCount));
    }

    @Test
    void GetStatement_ShouldThrowExceededMaxRequestedTransactionsException_WhenTransactionsRequestedGreaterThanMaxAllowedLimit() {
        // Arrange
        int transactionCount = BankAccountService.MAX_REQUESTED_TRANSACTIONS_LIMIT + 1;
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .withRandomTransactions(100, 1, 500)
                .build();

        accountRepository.save(account);

        // Act & Assert
        assertThrows(ExceededMaxRequestedTransactionsException.class, () -> accountService.getStatement(account.id, transactionCount));
    }

    @Test
    void GetStatement_ShouldReturnNTransactions_WhenNTransactionsRequested() throws ExceededMaxRequestedTransactionsException, InvalidAccountException {
        int transactionCount = 10;

        // Arrange
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .withRandomTransactions(100, 1, 500)
                .build();

        accountRepository.save(account);

        // Act
        var result = accountService.getStatement(account.id, transactionCount);

        // Assert
        assertEquals(transactionCount, result.transactions.size());
    }

    @Test
    void GetStatement_ShouldReturnTransactions_InDescendingOrderOfDate() throws ExceededMaxRequestedTransactionsException, InvalidAccountException {
        int transactionCount = 20;

        // Arrange
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .withRandomTransactions(100, 1, 500)
                .build();

        accountRepository.save(account);

        List<TransactionResult> expectedTransactions = account.transactions
                .stream()
                .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
                .limit(transactionCount)
                .map(TransactionResult::fromEntity)
                .toList();

        // Act
        var result = accountService.getStatement(account.id, transactionCount);

        // Assert
        assertEquals(expectedTransactions, result.transactions);
    }

    /**
     * ----------------------- GetAccountDetails Tests -----------------------
     */

    @Test
    void GetAccountDetails_ShouldThrowInvalidAccountException_WhenInvalidAccount() {
        // Arrange
        long invalidAccountId = 999;

        // Act & Assert
        assertThrows(InvalidAccountException.class, () -> accountService.getDetails(invalidAccountId));
    }

    @Test
    void GetAccountDetails_ShouldReturnAccountDetails_WhenValidAccount() throws InvalidAccountException {
        // Arrange
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(account);

        // Act
        var result = accountService.getDetails(account.id);

        // Assert
        assertEquals(account.id, result.accountId);
        assertEquals(account.isActive, result.isActive);
        assertEquals(account.currency.getCurrencyCode(), result.currency);
        assertTrue(account.isBalanceEquals(result.balance));
    }
}
