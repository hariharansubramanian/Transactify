package dev.hari.playground.modernbank.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Builder for {@link Account}, used for fluent property setting in Database seeding and test cases
 */
public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder isActive(boolean isActive) {
        this.account.setIsActive(isActive);
        return this;
    }

    public AccountBuilder withBalance(BigDecimal balance) {
        this.account.setBalance(balance);
        return this;
    }

    public AccountBuilder withCurrency(Currency currency) {
        this.account.setCurrency(currency);
        return this;
    }

    public Account build() {
        return account;
    }
}
