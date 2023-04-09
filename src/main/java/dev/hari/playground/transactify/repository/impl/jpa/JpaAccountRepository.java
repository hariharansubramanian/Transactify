package dev.hari.playground.transactify.repository.impl.jpa;

import dev.hari.playground.transactify.model.Account;
import dev.hari.playground.transactify.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA implementation of {@link AccountRepository}
 */

public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {

}
