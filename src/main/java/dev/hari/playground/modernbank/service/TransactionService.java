package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.model.Transaction;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Service behaviors for dealing with {@link Transaction}
 */
public interface TransactionService {


    /**
     * Get N transactions for an account ordered by sort function
     * @param accountId The account id to get transactions for
     * @param transactionCount The number of transactions to get
     * @param sort The sort function to sort transactions by
     * @return List of {@link Transaction}
     */
    List<Transaction> getTransactionsForAccount(long accountId, int transactionCount, Sort sort);
}