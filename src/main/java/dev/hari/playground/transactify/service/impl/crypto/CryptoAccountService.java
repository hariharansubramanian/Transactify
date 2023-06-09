package dev.hari.playground.transactify.service.impl.crypto;

import dev.hari.playground.transactify.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.transactify.dto.getDetails.GetAccountDetailsResult;
import dev.hari.playground.transactify.dto.getStatement.GetStatementResult;
import dev.hari.playground.transactify.exception.classes.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.transactify.exception.classes.InvalidAccountException;
import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.model.TransactionType;
import dev.hari.playground.transactify.service.AccountService;
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
