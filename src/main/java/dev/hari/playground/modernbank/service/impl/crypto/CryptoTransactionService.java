package dev.hari.playground.modernbank.service.impl.crypto;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;
import dev.hari.playground.modernbank.model.TransactionType;
import dev.hari.playground.modernbank.service.TransactionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cryptocurrency specific implementation of {@link TransactionService}
 */
public class CryptoTransactionService implements TransactionService {
    @Override
    public List<Transaction> getTransactionsForAccount(long accountId, int transactionCount, Sort sort) {
        return null;
    }

    @Override
    public void registerTransaction(Account account, TransactionType transactionType, BigDecimal amount) {

    }
}
