package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.*;
import dev.hari.playground.modernbank.model.builders.AccountBuilder;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BankPaymentServiceTests {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConversionService conversionService;

    /**
     * ----------------------- ProcessPayment Tests -----------------------
     */
    @Test
    void ProcessPayment_ShouldThrowInvalidAccountException_WhenInvalidSourceAccount() {
        // Arrange
        // Setup destination account
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();
        accountRepository.save(account);

        var destinationAccountId = account.id;
        var sourceAccountId = 999;

        BigDecimal amountToPay = BigDecimal.valueOf(100);
        PaymentRequest request = new PaymentRequest(sourceAccountId, destinationAccountId, amountToPay);

        // Act & Assert
        assertThrows(InvalidAccountException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldThrowInvalidAccountException_WhenInvalidDestinationAccount() {
        // Arrange
        // Setup source account
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();
        accountRepository.save(account);

        var sourceAccountId = account.id;
        var destinationAccountId = 999;

        // Act and Assert
        BigDecimal amountToPay = BigDecimal.valueOf(100);
        PaymentRequest request = new PaymentRequest(sourceAccountId, destinationAccountId, amountToPay);

        assertThrows(InvalidAccountException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldThrowPaymentRequestValidationException_WhenNegativeSourceAccountId() {
        // Arrange
        // Setup destination account
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();
        accountRepository.save(account);

        var destinationAccountId = account.id;
        var sourceAccountId = -1;

        // Act & Assert
        BigDecimal amountToPay = BigDecimal.valueOf(100);
        PaymentRequest request = new PaymentRequest(sourceAccountId, destinationAccountId, amountToPay);

        assertThrows(PaymentRequestValidationException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldThrowPaymentRequestValidationException_WhenNegativeDestinationAccountId() {
        // Arrange
        // Setup source account
        var account = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();
        accountRepository.save(account);

        var sourceAccountId = account.id;
        var destinationAccountId = -1;

        // Act & Assert
        BigDecimal amountToPay = BigDecimal.valueOf(100);
        PaymentRequest request = new PaymentRequest(sourceAccountId, destinationAccountId, amountToPay);

        assertThrows(PaymentRequestValidationException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldThrowPaymentRequestValidationException_WhenAmountIsNegativeOrZero() {
        // Arrange
        // Setup source account
        BigDecimal invalidAmount = BigDecimal.valueOf(0);
        var sourceAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        // Setup destination account
        var destinationAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        // Act & Assert
        PaymentRequest request = new PaymentRequest(sourceAccount.id, destinationAccount.id, invalidAmount);
        assertThrows(PaymentRequestValidationException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldThrowInsufficientFunds_WhenSourceAccountCannotAffordPayment() {
        // Arrange
        // Setup source account
        BigDecimal sourceBalance = BigDecimal.valueOf(1000);
        var sourceAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(sourceBalance)
                .withCurrency(Currency.getInstance("USD"))
                .build();

        // Setup destination account
        var destinationAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        // Act & Assert
        BigDecimal amountToPay = sourceBalance.add(BigDecimal.valueOf(1));
        PaymentRequest request = new PaymentRequest(sourceAccount.id, destinationAccount.id, amountToPay);

        assertThrows(InsufficientFundsException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldThrowInvalidAccountException_WhenSourceAccountIsDeactivated() {
        // Arrange
        // Setup source account
        BigDecimal sourceBalance = BigDecimal.valueOf(1000);
        var inactiveSrcAccount = new AccountBuilder()
                .isActive(false)
                .withBalance(sourceBalance)
                .withCurrency(Currency.getInstance("USD"))
                .build();

        // Setup destination account
        var destinationAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(inactiveSrcAccount);
        accountRepository.save(destinationAccount);

        // Act & Assert
        BigDecimal amountToPay = BigDecimal.valueOf(100);
        PaymentRequest request = new PaymentRequest(inactiveSrcAccount.id, destinationAccount.id, amountToPay);

        assertThrows(InvalidAccountException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldThrowInvalidAccountException_WhenDestinationAccountIsDeactivated() {
        // Arrange
        // Setup source account
        BigDecimal sourceBalance = BigDecimal.valueOf(1000);
        var sourceAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(sourceBalance)
                .withCurrency(Currency.getInstance("USD"))
                .build();

        // Setup destination account
        var inactiveDestinationAccount = new AccountBuilder()
                .isActive(false)
                .withBalance(BigDecimal.valueOf(1000))
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(sourceAccount);
        accountRepository.save(inactiveDestinationAccount);

        // Act & Assert
        BigDecimal amountToPay = BigDecimal.valueOf(100);
        PaymentRequest request = new PaymentRequest(sourceAccount.id, inactiveDestinationAccount.id, amountToPay);

        assertThrows(InvalidAccountException.class, () -> paymentService.processPayment(request));
    }

    @Test
    void ProcessPayment_ShouldTransferMoneyWithNoConversion_WhenSourceAndDestinationUseSameCurrency() throws PaymentRequestValidationException, InsufficientFundsException, ExchangeRatesFetchException, InvalidAccountException {
        // Arrange
        // Setup source account
        var amountToPay = BigDecimal.valueOf(500.23);
        var initialSourceBalance = BigDecimal.valueOf(1000.55);
        var sourceAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(initialSourceBalance)
                .withCurrency(Currency.getInstance("USD"))
                .build();

        // Setup destination account
        var initialDestinationBalance = BigDecimal.valueOf(1000.66);
        var destinationAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(initialDestinationBalance)
                .withCurrency(Currency.getInstance("USD"))
                .build();

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        BigDecimal expectedSourceBalance = initialSourceBalance.subtract(amountToPay);
        BigDecimal expectedDestinationBalance = initialDestinationBalance.add(amountToPay);

        // Act
        var request = new PaymentRequest(sourceAccount.id, destinationAccount.id, amountToPay);
        paymentService.processPayment(request);

        // Assert
        // TODO: See if we can avoid this extra call to DB
        var srcAccountFromDb = accountRepository.findAccountById(sourceAccount.id);
        var destAccountFromDB = accountRepository.findAccountById(destinationAccount.id);

        assertTrue(srcAccountFromDb.isBalanceEquals(expectedSourceBalance));
        assertTrue(destAccountFromDB.isBalanceEquals(expectedDestinationBalance));
    }

    @Test
    void ProcessPayment_ShouldTransferMoneyWithConversion_WhenSourceAndDestinationUseDifferentCurrency() throws ExchangeRatesFetchException, PaymentRequestValidationException, InsufficientFundsException, InvalidAccountException {
        // Arrange
        // Setup source account
        var amountToPay = BigDecimal.valueOf(500.23);
        var initialSourceBalance = BigDecimal.valueOf(1000.55);
        var sourceAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(initialSourceBalance)
                .withCurrency(Currency.getInstance("USD"))
                .build();

        // Setup destination account
        var initialDestinationBalance = BigDecimal.valueOf(1000.66);
        var destinationAccount = new AccountBuilder()
                .isActive(true)
                .withBalance(initialDestinationBalance)
                .withCurrency(Currency.getInstance("EUR"))
                .build();

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        // Calculate expected balances
        BigDecimal expectedSourceBalance = initialSourceBalance.subtract(amountToPay).setScale(2, RoundingMode.HALF_UP);
        var convertedAmount = conversionService.convert(amountToPay, sourceAccount.currency, destinationAccount.currency);
        BigDecimal expectedDestinationBalance = initialDestinationBalance.add(convertedAmount).setScale(2, RoundingMode.HALF_UP);

        // Act
        var request = new PaymentRequest(sourceAccount.id, destinationAccount.id, amountToPay);
        paymentService.processPayment(request);

        // Assert
        // TODO: See if we can avoid this extra call to DB
        var srcAccountFromDb = accountRepository.findAccountById(sourceAccount.id);
        var destAccountFromDb = accountRepository.findAccountById(destinationAccount.id);

        assertTrue(srcAccountFromDb.isBalanceEquals(expectedSourceBalance));
        assertTrue(destAccountFromDb.isBalanceEquals(expectedDestinationBalance));
    }
}
