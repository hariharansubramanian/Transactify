package dev.hari.playground.modernbank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;

/**
 * Entity representing a transaction
 * Note: all properties are public for ease of readability and lesser code, this is not a good practice in real world
 */
@Entity
public class Transaction {

    /* ------------------- Properties ------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    private BigDecimal amount;

    public Currency currency;

    @Enumerated(EnumType.STRING)
    public TransactionType type;

    // Note: ZonedDateTime is used instead of LocalDateTime because it is more suitable for storing date and time with time zone
    // FIXME: H2 does not support ZoneDateTime?
    private ZonedDateTime createdAt;

    /* ------------------- Relationships ------------------- */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    public Account account;

    /* ------------------- Getters & setters ------------------- */
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP); // Round to 2 decimal places
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt.truncatedTo(java.time.temporal.ChronoUnit.MICROS); // Truncate to microseconds (H2 does not support nanoseconds)
    }
}
