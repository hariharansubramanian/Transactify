package dev.hari.playground.transactify.repository.impl.jpa;

import dev.hari.playground.transactify.model.Transaction;
import dev.hari.playground.transactify.repository.TransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA implementation of {@link TransactionRepository}
 */

public interface JpaTransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepository {
}
