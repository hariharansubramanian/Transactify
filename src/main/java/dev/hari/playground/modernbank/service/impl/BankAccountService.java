package dev.hari.playground.modernbank.service.impl;

import dev.hari.playground.modernbank.dto.GetBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.GetStatement.GetStatementResult;
import dev.hari.playground.modernbank.dto.GetStatement.TransactionResult;
import dev.hari.playground.modernbank.exception.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.service.AccountService;
import dev.hari.playground.modernbank.service.TransactionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
        var account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new InvalidAccountException(String.format("Account with id %s does not exist", accountId));
        }

        return GetAccountBalanceResult.fromEntity(account);
    }

    @Override
    public GetStatementResult getStatement(long accountId, int transactionCount) throws InvalidAccountException, ExceededMaxRequestedTransactionsException {
        if (transactionCount > MAX_REQUESTED_TRANSACTIONS_LIMIT) {
            throw new ExceededMaxRequestedTransactionsException(String.format("Requested for %s transactions. Max limit is %s", transactionCount, MAX_REQUESTED_TRANSACTIONS_LIMIT));
        }

        var account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new InvalidAccountException(String.format("Account with id %s does not exist", accountId));
        }

        var transactions = transactionService.getTransactionsForAccount(accountId, transactionCount, Sort.by(Sort.Direction.DESC, "createdAt"));

        var result = new GetStatementResult();
        result.accountId = account.id;
        result.transactions = transactions
                .stream()
                .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
                .limit(transactionCount)
                .map(TransactionResult::fromEntity)
                .collect(Collectors.toList());

        return result;
    }
}
