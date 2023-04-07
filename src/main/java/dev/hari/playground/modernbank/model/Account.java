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

    private BigDecimal balance;

    private Currency currency;

    // Getters and setters
    public long getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
