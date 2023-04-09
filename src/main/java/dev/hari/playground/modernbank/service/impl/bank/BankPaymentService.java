package dev.hari.playground.modernbank.service.impl.bank;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.modernbank.exception.classes.InsufficientFundsException;
import dev.hari.playground.modernbank.exception.classes.InvalidAccountException;
import dev.hari.playground.modernbank.exception.classes.PaymentRequestValidationException;
import dev.hari.playground.modernbank.model.TransactionType;
import dev.hari.playground.modernbank.service.AccountService;
import dev.hari.playground.modernbank.service.ConversionService;
import dev.hari.playground.modernbank.service.PaymentService;
import dev.hari.playground.modernbank.service.TransactionService;
import jakarta.transaction.Transactional;

/**
 * Bank specific implementations of {@link PaymentService} behaviors for payments
 */
public class BankPaymentService implements PaymentService {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final ConversionService conversionService;

    public BankPaymentService(AccountService accountService, TransactionService transactionService, ConversionService conversionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional // make sure account balances and transactions are updated atomically
    public void processPayment(PaymentRequest request) throws PaymentRequestValidationException, InvalidAccountException, InsufficientFundsException, ExchangeRatesFetchException {
        // Validate the request
        request.Validate();

        // Get the source account
        var sourceAccount = accountService.getAccountOrThrow(request.sourceAccountId, true);

        // Check if the source account has sufficient funds
        if (!sourceAccount.canAffordAmount(request.amount)) {
            throw new InsufficientFundsException(String.format("Insufficient funds in account %s", sourceAccount.id));
        }

        // Get the destination account
        var destinationAccount = accountService.getAccountOrThrow(request.destinationAccountId, true);

        // Convert the amount to destination account currency
        var convertedAmount = conversionService.convert(request.amount, sourceAccount.currency, destinationAccount.currency);

        // Transfer the amount from source account to destination account
        TransactionType sourceTxType = TransactionType.DEBIT;
        TransactionType destinationTxType = TransactionType.CREDIT;

        accountService.updateBalance(sourceAccount, sourceTxType, request.amount);
        accountService.updateBalance(destinationAccount, destinationTxType, convertedAmount);

        // Register transactions in source and destination accounts
        transactionService.registerTransaction(sourceAccount, sourceTxType, request.amount);
        transactionService.registerTransaction(destinationAccount, destinationTxType, convertedAmount);
    }
}
