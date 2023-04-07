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

    // Getters and setters
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


}
