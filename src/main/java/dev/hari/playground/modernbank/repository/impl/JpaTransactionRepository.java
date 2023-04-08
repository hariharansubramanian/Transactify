package dev.hari.playground.modernbank.repository.impl;

import dev.hari.playground.modernbank.model.Transaction;
import dev.hari.playground.modernbank.repository.TransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of {@link TransactionRepository}
 */
@Repository
public interface JpaTransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepository {
}
