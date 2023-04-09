package dev.hari.playground.transactify.database;

import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.model.Transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * InMemory database to store data
 * This is a thread safe singleton class
 */
public class InMemoryDatabase {
    private static final InMemoryDatabase INSTANCE = new InMemoryDatabase();
    private final Map<Long, Account> accountMap = new ConcurrentHashMap<>();
    private final AtomicLong accountIdCounter = new AtomicLong(1);
    private final AtomicLong transactionIdCounter = new AtomicLong(1);

    private InMemoryDatabase() {
    }

    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

    public Map<Long, Account> getAccountMap() {
        return accountMap;
    }

    public long getNextAccountId() {
        return accountIdCounter.getAndIncrement();
    }

    public long getNextTransactionId() {
        return transactionIdCounter.getAndIncrement();
    }

    /**
     * Reset the database, used in tests only
     */
    public void reset() {
        accountMap.clear();
        accountIdCounter.set(1);
        transactionIdCounter.set(1);
    }

    /**
     * Save account and its transactions
     */
    public Account saveAccount(Account account) {
        // If the account is new, assign an id
        assignIdIfNew(account);

        // Assign transaction ids to new transactions
        account.getTransactions().forEach(this::assignIdIfNew);

        // Update the account
        accountMap.put(account.getId(), account);
        return account;
    }

    /**
     * Save transaction and add it to the account
     */
    public Transaction saveTransaction(Transaction transaction) {
        // If the transaction is new, assign an id
        assignIdIfNew(transaction);

        // Add transaction to account
        var accountId = transaction.getAccount().getId();
        Account account = getAccountMap().get(accountId);
        account.getTransactions().add(transaction);

        return transaction;

    }

    private void assignIdIfNew(Account account) {
        if (account.getId() == 0) {
            account.setId(getNextAccountId());
        }
    }

    private void assignIdIfNew(Transaction transaction) {
        if (transaction.getId() == 0) {
            transaction.setId(getNextTransactionId());
        }
    }
}
