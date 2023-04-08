package dev.hari.playground.modernbank.service.impl;

import dev.hari.playground.modernbank.dto.GetBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.GetStatement.GetStatementResult;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.service.AccountService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

/**
 * Bank specific implementations of {@link AccountService} behaviors for {@link Account}
 */
@Service
public class BankAccountService implements AccountService {

    private final AccountRepository accountRepository;

    public BankAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public GetAccountBalanceResult getBalance(long accountId) throws InvalidAccountException {
        var account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new InvalidAccountException("Account with id ${accountId} does not exist");
        }

        return GetAccountBalanceResult.fromEntity(account);
    }

    @Override
    public GetStatementResult getStatement(long accountId, int transactionCount) {
        throw new NotImplementedException("Not implemented yet");
    }
}
