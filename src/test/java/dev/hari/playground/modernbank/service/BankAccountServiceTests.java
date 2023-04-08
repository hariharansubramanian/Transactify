package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.GetBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.GetStatement.TransactionResult;
import dev.hari.playground.modernbank.exception.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.builders.AccountBuilder;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

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
    void GetStatement_ShouldThrowExceededMaxRequestedTransactionsException_WhenTransactionsRequestedGreaterThan50() {
        // Arrange
        int transactionCount = 51;
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
    void GetStatement_ShouldReturnNTransactions_WhenNTransactionsRequested() {
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
    void GetStatement_ShouldReturnTransactions_InDescendingOrderOfDate() {
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
                .sorted((t1, t2) -> t2.createdAt.compareTo(t1.createdAt))
                .limit(transactionCount)
                .map(t -> TransactionResult.fromEntity(t))
                .collect(Collectors.toList());

        // Act
        var result = accountService.getStatement(account.id, transactionCount);

        // Assert
        assertEquals(expectedTransactions, result.transactions);
    }
}
