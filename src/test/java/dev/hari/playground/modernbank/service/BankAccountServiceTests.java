package dev.hari.playground.modernbank.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BankAccountServiceTests {

    @Autowired
    private AccountService accountService;

    @Test
    public void GetAccountBalance_ThrowsInvalidAccountException_WhenInvalidAccount() {
        // Arrange
        long invalidAccountId = 999;

        // Act
        assertThrows(InvalidAccountException.class, () -> accountService.getAccountBalance(invalidAccountId));

        // Assert
    }

    @Test
    public void GetAccountBalance_ReturnsAccountBalance_WhenValidAccount() {
        // Arrange

        // Act

        // Assert
    }

}
