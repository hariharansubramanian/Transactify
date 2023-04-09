package dev.hari.playground.transactify.service;

import dev.hari.playground.transactify.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.transactify.dto.getDetails.GetAccountDetailsResult;
import dev.hari.playground.transactify.dto.getStatement.GetStatementResult;
import dev.hari.playground.transactify.exception.classes.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.transactify.exception.classes.InvalidAccountException;
import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.model.TransactionType;

import java.math.BigDecimal;

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

    /**
     * Get the account by account id
     * throws {@link InvalidAccountException} when account is not found.
     * throws {@link InvalidAccountException} when mustBeActive is true but account is not active
     *
     * @param accountId    The account id to get
     * @param mustBeActive - if true, account must be active
     * @return {@link Account}
     */

    Account getAccountOrThrow(long accountId, boolean mustBeActive) throws InvalidAccountException;


    /**
     * Get the account details
     *
     * @param accountId The account id to get details for
     * @return {@link GetAccountDetailsResult}
     */
    GetAccountDetailsResult getDetails(long accountId) throws InvalidAccountException;

    /**
     * Update the balance of an account
     *
     * @param account         - account to update balance for
     * @param transactionType - transaction type
     * @param amount          - amount to update
     */
    void updateBalance(Account account, TransactionType transactionType, BigDecimal amount);
}
