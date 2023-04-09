package dev.hari.playground.transactify.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Currency;

/**
 * Entity representing a transaction
 */
@Entity
public class Transaction {

    /* ------------------- Properties ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Amount of the transaction
     * Note: Recommended to use BigDecimal over double for financial calculations. See <a href="https://www.linkedin.com/pulse/why-we-should-use-bigdecimal-instead-double-java-financial-ismail/">this article</a>.
     */
    private BigDecimal amount;

    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    // Note: ZonedDateTime is used instead of LocalDateTime because it is more suitable for storing date and time with time zone
    private ZonedDateTime createdAt;

    /* ------------------- Relationships ------------------- */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    /* ------------------- Getters & setters ------------------- */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP); // Round to 2 decimal places
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt.truncatedTo(java.time.temporal.ChronoUnit.MICROS); // Truncate to microseconds (H2 does not support nanoseconds)
    }


    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
