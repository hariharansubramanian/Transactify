package dev.hari.playground.modernbank.model.builders;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;
import dev.hari.playground.modernbank.model.TransactionType;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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

    public AccountBuilder withRandomTransactions(int count, double minAmount, double maxAmount) {
        generateRandomTransactions(count, minAmount, maxAmount);
        return this;
    }

    /**
     * Generates count number of random transactions between min and max amount and updates the account balance
     */
    private void generateRandomTransactions(int count, double minAmount, double maxAmount) {
        // Generate count number of random transactions making sure the balance is positive
        IntStream.range(0, count).forEach(i -> {
            // Generate random amount and transaction type
            BigDecimal amount = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(minAmount, maxAmount)); // random amount between min and max
            TransactionType type = Math.random() < 0.5 && account.canAffordAmount(amount) ? TransactionType.DEBIT : TransactionType.CREDIT; // random type, DEBIT only when account can afford the amount

            // Create transaction
            Transaction transaction = new TransactionBuilder()
                    .withAmount(amount)
                    .withCurrency(account.currency)
                    .withType(type)
                    .withAccount(account)
                    .build();

            // Update account balance depending on transaction type
            account.balance = type == TransactionType.DEBIT ? account.balance.subtract(transaction.amount) : account.balance.add(transaction.amount);
            account.transactions.add(transaction);
        });
    }

    public Account build() {
        return account;
    }
}
