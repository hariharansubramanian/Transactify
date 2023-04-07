package dev.hari.playground.modernbank.repository;

import dev.hari.playground.modernbank.model.Account;

/**
 * Repository contracts for {@link Account}
 */
public interface AccountRepository {
    Account findAccountById(long accountId);
}
