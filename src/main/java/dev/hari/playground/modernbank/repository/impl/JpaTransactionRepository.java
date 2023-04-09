package dev.hari.playground.modernbank.repository.impl;

import dev.hari.playground.modernbank.model.Transaction;
import dev.hari.playground.modernbank.repository.TransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA implementation of {@link TransactionRepository}
 */

public interface JpaTransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepository {
}
