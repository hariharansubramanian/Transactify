package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.InsufficientFundsException;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.builders.AccountBuilder;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BankPaymentServiceTests {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AccountRepository accountRepository;

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

        PaymentRequest request = new PaymentRequest();
        request.sourceAccountId = sourceAccountId;
        request.destinationAccountId = destinationAccountId;
        request.amount = BigDecimal.valueOf(100);

        // Act
        paymentService.processPayment(request);

        // Assert
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

        PaymentRequest request = new PaymentRequest();
        request.sourceAccountId = sourceAccountId;
        request.destinationAccountId = destinationAccountId;
        request.amount = BigDecimal.valueOf(100);

        // Act
        paymentService.processPayment(request);

        // Assert
        assertThrows(InvalidAccountException.class, () -> paymentService.processPayment(request));
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

        // Act
        PaymentRequest request = new PaymentRequest();
        request.sourceAccountId = sourceAccount.id;
        request.destinationAccountId = destinationAccount.id;
        request.amount = sourceBalance.add(BigDecimal.valueOf(1));

        paymentService.processPayment(request);

        // Assert
        assertThrows(InsufficientFundsException.class, () -> paymentService.processPayment(request));
    }

//    @Test
//    void ProcessPayment_ShouldTransferMoneyWithNoConversion_WhenSourceAndDestinationUseSameCurrency() {
//        // Arrange
//        // Setup source account
//        var amountToPay = BigDecimal.valueOf(500.23);
//        var initialSourceBalance = BigDecimal.valueOf(1000.55);
//        var sourceAccount = new AccountBuilder()
//                .isActive(true)
//                .withBalance(initialSourceBalance)
//                .withCurrency(Currency.getInstance("USD"))
//                .build();
//
//        // Setup destination account
//        var initialDestinationBalance = BigDecimal.valueOf(1000.66);
//        var destinationAccount = new AccountBuilder()
//                .isActive(true)
//                .withBalance(initialDestinationBalance)
//                .withCurrency(Currency.getInstance("USD"))
//                .build();
//
//        accountRepository.save(sourceAccount);
//        accountRepository.save(destinationAccount);
//
//        BigDecimal expectedSourceBalance = initialSourceBalance.subtract(amountToPay);
//        BigDecimal expectedDestinationBalance = initialDestinationBalance.add(amountToPay);
//
//        // Act
//        PaymentTransferRequest request = new PaymentTransferRequest();
//        request.sourceAccountId = sourceAccount.id;
//        request.destinationAccountId = destinationAccount.id;
//        request.amount = amountToPay;
//
//        paymentService.processPayment(request);
//
//        // Assert
//        // TODO: See if we can avoid this extra call to DB
//        var actualSourceAccount = accountRepository.findAccountById(sourceAccount.id);
//        var destinationSourceAccount = accountRepository.findAccountById(destinationAccount.id);
//
//        assertTrue(actualSourceAccount.isBalanceEquals(expectedSourceBalance));
//        assertTrue(destinationSourceAccount.isBalanceEquals(expectedDestinationBalance));
//    }
//
//    @Test
//    void ProcessPayment_ShouldTransferMoneyWithConversion_WhenSourceAndDestinationUseDifferentCurrency() {
//        // Arrange
//        // Setup source account
//        var amountToPay = BigDecimal.valueOf(500.23);
//        var initialSourceBalance = BigDecimal.valueOf(1000.55);
//        var sourceAccount = new AccountBuilder()
//                .isActive(true)
//                .withBalance(initialSourceBalance)
//                .withCurrency(Currency.getInstance("USD"))
//                .build();
//
//        // Setup destination account
//        var initialDestinationBalance = BigDecimal.valueOf(1000.66);
//        var destinationAccount = new AccountBuilder()
//                .isActive(true)
//                .withBalance(initialDestinationBalance)
//                .withCurrency(Currency.getInstance("EUR"))
//                .build();
//
//        accountRepository.save(sourceAccount);
//        accountRepository.save(destinationAccount);
//
//        BigDecimal expectedSourceBalance = initialSourceBalance.subtract(amountToPay);
//
//        var convertedAmount = ConvertCurrency(amountToPay, sourceAccount.currency, destinationAccount.currency);
//        BigDecimal expectedDestinationBalance = initialDestinationBalance.add(convertedAmount);
//
//        // Act
//        PaymentTransferRequest request = new PaymentTransferRequest();
//        request.sourceAccountId = sourceAccount.id;
//        request.destinationAccountId = destinationAccount.id;
//        request.amount = amountToPay;
//
//        paymentService.processPayment(request);
//
//        // Assert
//        // TODO: See if we can avoid this extra call to DB
//        var actualSourceAccount = accountRepository.findAccountById(sourceAccount.id);
//        var destinationSourceAccount = accountRepository.findAccountById(destinationAccount.id);
//
//        assertTrue(actualSourceAccount.isBalanceEquals(expectedSourceBalance));
//        assertTrue(destinationSourceAccount.isBalanceEquals(expectedDestinationBalance));
//    }
}
