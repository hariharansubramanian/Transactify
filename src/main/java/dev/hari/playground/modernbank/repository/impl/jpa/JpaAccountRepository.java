package dev.hari.playground.modernbank.repository.impl.jpa;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA implementation of {@link AccountRepository}
 */

public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {

}
