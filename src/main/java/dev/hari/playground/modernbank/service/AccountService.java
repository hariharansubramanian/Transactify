package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.GetAccountBalanceResult;
import dev.hari.playground.modernbank.model.Account;

/**
 * Service behaviors for dealing with {@link Account}
 */
public interface AccountService {

    /**
     * Get the balance of an account
     *
     * @param accountId The account id to get balance for
     * @return {@link GetAccountBalanceResult}
     */
    GetAccountBalanceResult getAccountBalance(long accountId);

}
