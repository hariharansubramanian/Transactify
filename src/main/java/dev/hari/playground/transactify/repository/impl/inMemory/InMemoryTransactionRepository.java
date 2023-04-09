package dev.hari.playground.transactify.repository.impl.inMemory;

import dev.hari.playground.transactify.database.InMemoryDatabase;
import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.model.Transaction;
import dev.hari.playground.transactify.repository.TransactionRepository;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * InMemory implementation of {@link TransactionRepository}
 */

public class InMemoryTransactionRepository implements TransactionRepository {
    private final InMemoryDatabase db;

    public InMemoryTransactionRepository() {
        db = InMemoryDatabase.getInstance();
    }

    @Override
    public List<Transaction> findAllByAccountId(long accountId, Pageable pageable) {
        Account account = db.getAccountMap().get(accountId);
        if (account == null) {
            return Collections.emptyList();
        }

        return account.transactions
                .stream()
                .filter(transaction -> transaction.account.id == accountId)
                .sorted(Comparator.comparing(Transaction::getCreatedAt, Comparator.reverseOrder()))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return db.saveTransaction(transaction);
    }
}
