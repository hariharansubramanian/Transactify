package dev.hari.playground.transactify.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Entity representing an account
 */
@Entity
public class Account {

    /* ------------------- Properties ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean isActive;

    /**
     * Balance of the account
     * Note: Recommended to use BigDecimal over double for financial calculations. See <a href="https://www.linkedin.com/pulse/why-we-should-use-bigdecimal-instead-double-java-financial-ismail/">this article</a>.
     */

    private BigDecimal balance = BigDecimal.ZERO;

    private Currency currency;

    /* ------------------- Relationships ------------------- */
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, targetEntity = Transaction.class, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    /* ------------------- Business methods ------------------- */

    // TODO: Create BigDecimal extension methods for BigDecimal specific operations

    /**
     * Check if the balance of the account is equal to the given balance by ignoring the scale of precisions
     * Note: 1000.00 and 1000.000 are not equal when using {@link BigDecimal#equals(Object)}, but they are equal when using {@link BigDecimal#compareTo(BigDecimal)}
     */
    public boolean isBalanceEquals(BigDecimal balance) {
        return this.getBalance().compareTo(balance) == 0;
    }

    /**
     * Check if the account balance is sufficient to afford the given amount
     */
    public boolean canAffordAmount(BigDecimal amount) {
        return this.getBalance().compareTo(amount) >= 0;
    }


    /* ------------------- Getters & setters ------------------- */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, RoundingMode.HALF_UP); // Round to 2 decimal places
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
