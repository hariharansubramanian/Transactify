package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.GetBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.GetStatement.GetStatementResult;
import dev.hari.playground.modernbank.exception.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
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
    GetAccountBalanceResult getBalance(long accountId) throws InvalidAccountException;

    /**
     * Get the statement of an account for the last N transactions (N = transactionCount)
     *
     * @param accountId        The account id to get statement for
     * @param transactionCount The number of transactions to get statement for
     * @return {@link GetStatementResult}
     */
    GetStatementResult getStatement(long accountId, int transactionCount) throws InvalidAccountException, ExceededMaxRequestedTransactionsException;
}
