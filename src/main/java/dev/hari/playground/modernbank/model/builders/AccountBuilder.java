package dev.hari.playground.modernbank.model.builders;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Builder for {@link Account}, used for fluent property setting in database seeding and test cases
 */
public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder isActive(boolean isActive) {
        this.account.isActive = isActive;
        return this;
    }

    public AccountBuilder withBalance(BigDecimal balance) {
        this.account.balance = balance;
        return this;
    }

    public AccountBuilder withCurrency(Currency currency) {
        this.account.currency = currency;
        return this;
    }

    public AccountBuilder withTransaction(Transaction transaction) {
        transaction.account = this.account;
        this.account.transactions.add(transaction);
        return this;
    }

    public Account build() {
        return account;
    }
}
