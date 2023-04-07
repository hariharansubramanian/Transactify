package dev.hari.playground.modernbank.repository.impl;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of {@link AccountRepository}
 */
@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {

}
