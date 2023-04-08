package dev.hari.playground.modernbank.dto;

import dev.hari.playground.modernbank.model.Account;

import java.math.BigDecimal;

/**
 * DTO contract returned as a response to GET API /account/{id}/balance
 */
public class GetAccountBalanceResult {
    public long accountId;
    public String currency;
    public BigDecimal balance;

    /**
     * Factory method to create DTO from {@link Account} entity
     */
    public static GetAccountBalanceResult fromEntity(Account account) {
        return new GetAccountBalanceResult() {{
            accountId = account.getId();
            currency = account.getCurrency().getDisplayName();
            balance = account.getBalance();
        }};
    }
}
