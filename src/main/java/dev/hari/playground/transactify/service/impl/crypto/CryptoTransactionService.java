package dev.hari.playground.transactify.service.impl.crypto;

import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.model.Transaction;
import dev.hari.playground.transactify.model.TransactionType;
import dev.hari.playground.transactify.service.TransactionService;
import org.springframework.data.domain.Sort;

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
