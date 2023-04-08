package dev.hari.playground.modernbank.service.impl;

import dev.hari.playground.modernbank.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.getStatement.GetStatementResult;
import dev.hari.playground.modernbank.dto.getStatement.TransactionResult;
import dev.hari.playground.modernbank.exception.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.service.AccountService;
import dev.hari.playground.modernbank.service.TransactionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Bank specific implementations of {@link AccountService} behaviors for {@link Account}
 */
@Service
public class BankAccountService implements AccountService {
    /**
     * Max number of transactions that can be requested for a statement, this is to prevent a client from requesting for a huge number of transactions and causing a performance issue
     * //TODO: This should ideally be a configurable application property
     */
    public static final int MAX_REQUESTED_TRANSACTIONS_LIMIT = 50;

    private final AccountRepository accountRepository;

    private final TransactionService transactionService;


    public BankAccountService(AccountRepository accountRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    @Override
    public GetAccountBalanceResult getBalance(long accountId) throws InvalidAccountException {
        // Get the account or throw an exception if account does not exist
        var account = getAccountOrThrow(accountId);

        // Create the result
        return GetAccountBalanceResult.fromEntity(account);
    }

    @Override
    public GetStatementResult getStatement(long accountId, int transactionCount) throws InvalidAccountException, ExceededMaxRequestedTransactionsException {
        // Limit the number of transactions that can be requested for a statement
        if (transactionCount > MAX_REQUESTED_TRANSACTIONS_LIMIT) {
            throw new ExceededMaxRequestedTransactionsException(String.format("Requested for %s transactions. Max limit is %s", transactionCount, MAX_REQUESTED_TRANSACTIONS_LIMIT));
        }

        // Get the account or throw an exception if account does not exist
        var account = getAccountOrThrow(accountId);

        // Get the transactions for the account
        var accountTransactions = transactionService.getTransactionsForAccount(accountId, transactionCount, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Create the result
        return GetStatementResult.fromEntity(account, accountTransactions);
    }

    /**
     * Get account by id or throw {@link InvalidAccountException} if account does not exist
     *
     * @param accountId The account id to get
     * @return {@link Account} if it exists
     * @throws InvalidAccountException if account does not exist
     */
    @Override
    public Account getAccountOrThrow(long accountId) throws InvalidAccountException {
        var account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new InvalidAccountException(String.format("Account with id %s does not exist", accountId));
        }

        return account;
    }
}
