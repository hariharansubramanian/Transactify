package dev.hari.playground.modernbank.dto.getBalance;

import dev.hari.playground.modernbank.model.Account;

import java.math.BigDecimal;

/**
 * DTO contract returned as a response to GET API /account/{id}/balance
 */
public class GetAccountBalanceResult {
    public long accountId;
    public String currency;
    public BigDecimal balance;

    public GetAccountBalanceResult() {
        // Empty constructor for serialization
    }

    public GetAccountBalanceResult(long accountId, String currency, BigDecimal balance) {
        this.accountId = accountId;
        this.currency = currency;
        this.balance = balance;
    }

    /**
     * Factory method to create DTO from {@link Account} entity
     */
    public static GetAccountBalanceResult fromEntity(Account account) {
        var result = new GetAccountBalanceResult();
        result.accountId = account.id;
        result.currency = account.currency.getCurrencyCode();
        result.balance = account.balance;
        return result;
    }
}
