package dev.hari.playground.modernbank.repository;

import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.Transaction;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Repository contracts for {@link Transaction}
 */
public interface TransactionRepository {
    /**
     * Find the transactions for an account
     *
     * @param accountId The account id to get transactions for
     * @param pageable  The page request with sort and page size
     * @return List of {@link Transaction}
     */
    List<Transaction> findAllByAccountId(long accountId, Pageable pageable);

    /**
     * Save a transaction
     *
     * @param transaction The transaction to save
     */
    Transaction save(Transaction transaction);
}
