package dev.hari.playground.transactify.model.builders;

import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.model.Transaction;
import dev.hari.playground.transactify.model.TransactionType;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Currency;

/**
 * Builder for {@link Transaction}, used for fluent property setting in database seeding and test cases
 */
public class TransactionBuilder {

    private final Transaction transaction;

    public TransactionBuilder() {
        this.transaction = new Transaction();
        this.transaction.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
    }

    public TransactionBuilder withAmount(BigDecimal amount) {
        this.transaction.setAmount(amount);
        return this;
    }

    public TransactionBuilder withCurrency(Currency currency) {
        this.transaction.currency = currency;
        return this;
    }

    public TransactionBuilder withType(TransactionType type) {
        this.transaction.type = type;
        return this;
    }

    public TransactionBuilder withCreatedAt(ZonedDateTime createdAt) {
        this.transaction.setCreatedAt(createdAt);
        return this;
    }

    public TransactionBuilder withAccount(Account account) {
        this.transaction.account = account;
        return this;
    }

    public Transaction build() {
        return transaction;
    }
}
