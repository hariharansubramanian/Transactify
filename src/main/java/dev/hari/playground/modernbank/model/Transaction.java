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

    public BigDecimal amount;

    public Currency currency;

    @Enumerated(EnumType.STRING)
    public TransactionType type;

    // Note: ZonedDateTime is used instead of LocalDateTime because it is more suitable for storing date and time with time zone
    // FIXME: H2 does not support ZoneDateTime?
    public ZonedDateTime createdAt;

    /* ------------------- Relationships ------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account account;
}
