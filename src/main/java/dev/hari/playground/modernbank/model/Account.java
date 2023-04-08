package dev.hari.playground.modernbank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Entity representing an account
 * Note: all properties are public for ease of readability and lesser code, this is not a good practice in real world
 */
@Entity
public class Account {

    /* ------------------- Properties ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public boolean isActive;

    /**
     * Balance of the account
     * Note: Recommended to use BigDecimal over double for financial calculations. See <a href="https://www.linkedin.com/pulse/why-we-should-use-bigdecimal-instead-double-java-financial-ismail/">this article</a>.
     */
    public BigDecimal balance = BigDecimal.ZERO;

    public Currency currency;

    /* ------------------- Relationships ------------------- */
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, targetEntity = Transaction.class, cascade = CascadeType.ALL)
    public List<Transaction> transactions = new ArrayList<>();

    /* ------------------- Business methods ------------------- */

    // TODO: Create BigDecimal extension methods for BigDecimal specific operations

    /**
     * Check if the balance of the account is equal to the given balance by ignoring the scale of precisions
     * Note: 1000.00 and 1000.000 are not equal when using {@link BigDecimal#equals(Object)}, but they are equal when using {@link BigDecimal#compareTo(BigDecimal)}
     */
    public boolean isBalanceEquals(BigDecimal balance) {
        return this.balance.compareTo(balance) == 0;
    }

    /**
     * Check if the account balance is sufficient to afford the given amount
     */
    public boolean canAffordAmount(BigDecimal amount) {
        return this.balance.compareTo(amount) >= 0;
    }
}
