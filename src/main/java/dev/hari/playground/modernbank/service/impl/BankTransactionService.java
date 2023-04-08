package dev.hari.playground.modernbank.service.impl;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;
import dev.hari.playground.modernbank.model.TransactionType;
import dev.hari.playground.modernbank.model.builders.TransactionBuilder;
import dev.hari.playground.modernbank.repository.TransactionRepository;
import dev.hari.playground.modernbank.service.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Bank specific implementations of {@link TransactionService} behaviors for {@link Transaction}
 */
@Service
public class BankTransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;

    public BankTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getTransactionsForAccount(long accountId, int transactionCount, Sort sort) {
        return transactionRepository.findAllByAccountId(accountId, PageRequest.of(0, transactionCount, sort));
    }

    @Override
    public void registerTransaction(Account account, TransactionType transactionType, BigDecimal amount) {
        // Create transaction of type transactionType
        var transaction = new TransactionBuilder()
                .withAccount(account)
                .withCurrency(account.currency)
                .withType(transactionType)
                .withAmount(amount)
                .build();

        // Save the transaction
        transactionRepository.save(transaction);
    }
}
