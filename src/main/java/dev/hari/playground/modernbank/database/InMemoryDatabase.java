package dev.hari.playground.modernbank.database;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;

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
     * Save account and its transactions
     */
    public Account saveAccount(Account account) {
        // If the account is new, assign an id
        assignIdIfNew(account);

        // Assign transaction ids to new transactions
        account.transactions.forEach(this::assignIdIfNew);

        // Update the account
        accountMap.put(account.id, account);
        return account;
    }

    /**
     * Save transaction and add it to the account
     */
    public Transaction saveTransaction(Transaction transaction) {
        // If the transaction is new, assign an id
        assignIdIfNew(transaction);

        // Add transaction to account
        var accountId = transaction.account.id;
        Account account = getAccountMap().get(accountId);
        account.transactions.add(transaction);

        return transaction;

    }

    private void assignIdIfNew(Account account) {
        if (account.id == 0) {
            account.id = getNextAccountId();
        }
    }

    private void assignIdIfNew(Transaction transaction) {
        if (transaction.id == 0) {
            transaction.id = getNextTransactionId();
        }
    }
}