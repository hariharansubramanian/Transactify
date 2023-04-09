package dev.hari.playground.modernbank.dto.getDetails;

import dev.hari.playground.modernbank.model.Account;

import java.math.BigDecimal;

/**
 * DTO contract returned as a response to GET API /account/{id}/details
 */
public class GetAccountDetailsResult {
    public long accountId;
    public boolean isActive;
    public String currency;
    public BigDecimal balance;


    public GetAccountDetailsResult() {
        // Empty constructor for serialization
    }

    public GetAccountDetailsResult(long accountId, boolean isActive, String currency, BigDecimal balance) {
        this.accountId = accountId;
        this.isActive = isActive;
        this.currency = currency;
        this.balance = balance;
    }

    /**
     * Factory method to create DTO from {@link Account} entity
     */
    public static GetAccountDetailsResult fromEntity(Account account) {
        var result = new GetAccountDetailsResult();
        result.accountId = account.id;
        result.isActive = account.isActive;
        result.currency = account.currency.getCurrencyCode();
        result.balance = account.balance;
        return result;
    }
}
