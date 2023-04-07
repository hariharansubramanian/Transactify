package dev.hari.playground.modernbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
public class Account {

    // Properties
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean isActive;

    /**
     * Balance of the account
     * Note: Recommended to use BigDecimal over double for financial calculations. See <a href="https://www.linkedin.com/pulse/why-we-should-use-bigdecimal-instead-double-java-financial-ismail/">this article</a>.
     */
    private BigDecimal balance;

    private Currency currency;

    // Getters & Setters

    public long getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    // Business Methods

    /**
     * Check if the balance of the account is equal to the given balance by ignoring the scale of precisions
     * Note: 1000.00 and 1000.000 are not equal when using {@link BigDecimal#equals(Object)}, but they are equal when using {@link BigDecimal#compareTo(BigDecimal)}
     */
    public boolean isBalanceEqualTo(BigDecimal balance) {
        return this.balance.compareTo(balance) == 0;
    }
}
