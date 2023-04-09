package dev.hari.playground.modernbank.repository.impl;

import dev.hari.playground.modernbank.database.InMemoryDatabase;
import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.springframework.stereotype.Repository;

/**
 * InMemory implementation of {@link AccountRepository}
 */
@Repository
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
