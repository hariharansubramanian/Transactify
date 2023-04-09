package dev.hari.playground.modernbank.service.impl.bank;

import dev.hari.playground.modernbank.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.getDetails.GetAccountDetailsResult;
import dev.hari.playground.modernbank.dto.getStatement.GetStatementResult;
import dev.hari.playground.modernbank.exception.classes.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.classes.InvalidAccountException;
import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.TransactionType;
import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.service.AccountService;
import dev.hari.playground.modernbank.service.TransactionService;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

/**
 * Bank specific implementations of {@link AccountService} behaviors for {@link Account}
 */
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
        var account = getAccountOrThrow(accountId, false);

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
        var account = getAccountOrThrow(accountId, false);

        // Get the transactions for the account
        var accountTransactions = transactionService.getTransactionsForAccount(accountId, transactionCount, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Create the result
        return GetStatementResult.fromEntity(account, accountTransactions);
    }

    @Override
    public Account getAccountOrThrow(long accountId, boolean mustBeActive) throws InvalidAccountException {
        // Get the account
        var account = accountRepository.findAccountById(accountId);

        // Throw an exception if account does not exist or is not active
        if ((account == null) || (mustBeActive && !account.isActive)) {
            throw new InvalidAccountException(String.format("Account with id %s is invalid", accountId));
        }

        return account;
    }

    @Override
    public GetAccountDetailsResult getDetails(long accountId) throws InvalidAccountException {

        // Get the account or throw an exception if account does not exist
        var account = getAccountOrThrow(accountId, false);

        // Create the result
        return GetAccountDetailsResult.fromEntity(account);
    }

    @Override
    public void updateBalance(Account account, TransactionType transactionType, BigDecimal amount) {
        // Get the current balance
        var currentBalance = account.balance;

        // Update the balance based on the transaction type
        switch (transactionType) {
            case CREDIT -> account.balance = currentBalance.add(amount);
            case DEBIT -> account.balance = currentBalance.subtract(amount);
        }

        // Save the account
        accountRepository.save(account);
    }
}
