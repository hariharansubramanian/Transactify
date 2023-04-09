package dev.hari.playground.transactify.dto.getDetails;

import dev.hari.playground.transactify.model.Account;

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

        result.accountId = account.getId();
        result.isActive = account.isActive();
        result.currency = account.getCurrency().getCurrencyCode();
        result.balance = account.getBalance();

        return result;
    }
}
