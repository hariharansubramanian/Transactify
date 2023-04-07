package dev.hari.playground.modernbank.repository;

import dev.hari.playground.modernbank.model.Account;

/**
 * Repository contracts for {@link Account}
 */
public interface AccountRepository {

    /**
     * Find an account by id
     *
     * @param accountId The account id to find
     * @return {@link Account}
     */
    Account findAccountById(long accountId);

    /**
     * Save an account
     *
     * @param account The account to save
     * @return {@link Account}
     */
    Account save(Account account);
}
