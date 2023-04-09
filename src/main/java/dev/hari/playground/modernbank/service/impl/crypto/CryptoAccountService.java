package dev.hari.playground.modernbank.service.impl.crypto;

import dev.hari.playground.modernbank.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.getDetails.GetAccountDetailsResult;
import dev.hari.playground.modernbank.dto.getStatement.GetStatementResult;
import dev.hari.playground.modernbank.exception.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.InvalidAccountException;
import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.TransactionType;
import dev.hari.playground.modernbank.service.AccountService;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;

/**
 * Cryptocurrency specific implementation of {@link AccountService}
 */
public class CryptoAccountService implements AccountService {
    @Override
    public GetAccountBalanceResult getBalance(long accountId) throws InvalidAccountException {
        throw new NotImplementedException("getBalance for Crypto not implemented yet");
    }

    @Override
    public GetStatementResult getStatement(long accountId, int transactionCount) throws InvalidAccountException, ExceededMaxRequestedTransactionsException {
        throw new NotImplementedException("getStatement for Crypto not implemented yet");
    }

    @Override
    public Account getAccountOrThrow(long accountId, boolean mustBeActive) throws InvalidAccountException {
        throw new NotImplementedException("getAccountOrThrow for Crypto not implemented yet");
    }

    @Override
    public GetAccountDetailsResult getDetails(long accountId) throws InvalidAccountException {
        throw new NotImplementedException("getDetails for Crypto not implemented yet");
    }

    @Override
    public void updateBalance(Account account, TransactionType transactionType, BigDecimal amount) {
        throw new NotImplementedException("updateBalance for Crypto not implemented yet");
    }
}
