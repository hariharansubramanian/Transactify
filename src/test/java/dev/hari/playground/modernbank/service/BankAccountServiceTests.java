package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.GetAccountBalanceResult;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.AccountBuilder;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankAccountServiceTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void GetAccountBalance_ShouldThrowInvalidAccountException_WhenInvalidAccount() {
        // Arrange
        long invalidAccountId = 999;

        // Act & Assert
        assertThrows(InvalidAccountException.class, () -> accountService.getAccountBalance(invalidAccountId));
    }

    @Test
    void GetAccountBalance_ShouldReturnAccountBalance_WhenValidAccount() throws InvalidAccountException {
        // Arrange
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(account);

        // Act
        GetAccountBalanceResult result = accountService.getAccountBalance(account.getId());

        // Assert
        assertTrue(account.isBalanceEqualTo(result.balance));
    }
}
