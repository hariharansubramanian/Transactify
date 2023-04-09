package dev.hari.playground.transactify.repository.impl.inMemory;

import dev.hari.playground.transactify.database.InMemoryDatabase;
import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.repository.AccountRepository;

/**
 * InMemory implementation of {@link AccountRepository}
 */

public class InMemoryAccountRepository implements AccountRepository {
    private final InMemoryDatabase db;

    public InMemoryAccountRepository() {
        db = InMemoryDatabase.getInstance();
    }

    @Override
    public Account findAccountById(long accountId) {
        return db.getAccountMap().get(accountId);
    }

    @Override
    public Account save(Account account) {
        return db.saveAccount(account);
    }
}
