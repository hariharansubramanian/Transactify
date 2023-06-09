package dev.hari.playground.transactify.model.builders;

import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.model.Transaction;
import dev.hari.playground.transactify.model.TransactionType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Builder for {@link Account}, used for fluent property setting in database seeding and test cases
 */
public class AccountBuilder {

    private final Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder isActive(boolean isActive) {
        this.account.setActive(isActive);
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

    public AccountBuilder withRandomTransactions(int count, double minAmount, double maxAmount) {
        generateRandomTransactions(count, minAmount, maxAmount);
        return this;
    }

    /**
     * Generates count number of random transactions between min and max amount and updates the account balance
     */
    private void generateRandomTransactions(int count, double minAmount, double maxAmount) {
        var createdAt = ZonedDateTime.now().minus(1, java.time.temporal.ChronoUnit.DAYS);
        // Generate count number of random transactions making sure the balance is positive
        IntStream.range(0, count).forEach(i -> {
            // Generate random amount and transaction type
            BigDecimal amount = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(minAmount, maxAmount)); // random amount between min and max
            TransactionType type = Math.random() < 0.5 && account.canAffordAmount(amount) ? TransactionType.DEBIT : TransactionType.CREDIT; // random type, DEBIT only when account can afford the amount

            // Create transaction
            Transaction transaction = new TransactionBuilder()
                    .withAmount(amount)
                    .withCurrency(account.getCurrency())
                    .withType(type)
                    .withAccount(account) // FIXME: the account should ideally be inferred since its a foreign key, but without this, the transaction gets a null account and fails to save
                    .withCreatedAt(createdAt.plus(i, java.time.temporal.ChronoUnit.SECONDS)) // Add i seconds to createdAt to make sure the transactions are not created at the same time
                    .build();

            // Update account balance depending on transaction type
            account.setBalance(type == TransactionType.DEBIT ? account.getBalance().subtract(transaction.getAmount()) : account.getBalance().add(transaction.getAmount()));
            account.getTransactions().add(transaction);
        });
    }

    public Account build() {
        return account;
    }
}
