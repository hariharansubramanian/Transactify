package dev.hari.playground.modernbank.model.builders;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;
import dev.hari.playground.modernbank.model.TransactionType;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Currency;

/**
 * Builder for {@link Transaction}, used for fluent property setting in database seeding and test cases
 */
public class TransactionBuilder {

    private Transaction transaction;

    public TransactionBuilder() {
        this.transaction = new Transaction();
        this.transaction.createdAt = ZonedDateTime.now(ZoneOffset.UTC);
    }

    public TransactionBuilder withAmount(BigDecimal amount) {
        this.transaction.amount = amount;
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
        this.transaction.createdAt = createdAt;
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