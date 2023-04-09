package dev.hari.playground.modernbank.config;

import dev.hari.playground.modernbank.client.ExchangeRatesClient;
import dev.hari.playground.modernbank.repository.AccountRepository;
import dev.hari.playground.modernbank.repository.TransactionRepository;
import dev.hari.playground.modernbank.service.AccountService;
import dev.hari.playground.modernbank.service.ConversionService;
import dev.hari.playground.modernbank.service.PaymentService;
import dev.hari.playground.modernbank.service.TransactionService;
import dev.hari.playground.modernbank.service.impl.bank.BankAccountService;
import dev.hari.playground.modernbank.service.impl.bank.BankPaymentService;
import dev.hari.playground.modernbank.service.impl.bank.BankTransactionService;
import dev.hari.playground.modernbank.service.impl.bank.FiatConversionService;
import dev.hari.playground.modernbank.service.impl.crypto.CryptoAccountService;
import dev.hari.playground.modernbank.service.impl.crypto.CryptoConversionService;
import dev.hari.playground.modernbank.service.impl.crypto.CryptoPaymentService;
import dev.hari.playground.modernbank.service.impl.crypto.CryptoTransactionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ServiceConfig {

    /**
     * --------------------------- Bank Services ---------------------------
     */
    @Bean
    @ConditionalOnProperty(name = {"bank.enabled"}, havingValue = "true")
    public AccountService bankAccountService(AccountRepository accountRepository, TransactionService transactionService) {
        return new BankAccountService(accountRepository, transactionService);
    }

    @Bean
    @ConditionalOnProperty(name = {"bank.enabled"}, havingValue = "true")
    public TransactionService bankTransactionService(TransactionRepository transactionRepository) {
        return new BankTransactionService(transactionRepository);
    }

    @Bean
    @ConditionalOnProperty(name = {"bank.enabled"}, havingValue = "true")
    public PaymentService bankPaymentService(AccountService accountService, TransactionService transactionService, ConversionService conversionService, ExchangeRatesClient exchangeRatesClient) {
        return new BankPaymentService(accountService, transactionService, new FiatConversionService(exchangeRatesClient));
    }

    @Bean
    @ConditionalOnProperty(name = {"bank.enabled"}, havingValue = "true")
    @Primary
    public ConversionService bankConversionService(ExchangeRatesClient exchangeRatesClient) {
        return new FiatConversionService(exchangeRatesClient);
    }

    /**
     * --------------------------- Crypto Services ---------------------------
     */

    @Bean
    @ConditionalOnProperty(name = {"crypto.enabled"}, havingValue = "true")
    public CryptoAccountService cryptoAccountService() {
        return new CryptoAccountService();
    }

    @Bean
    @ConditionalOnProperty(name = {"crypto.enabled"}, havingValue = "true")
    public CryptoTransactionService cryptoTransactionService() {
        return new CryptoTransactionService();
    }

    @Bean
    @ConditionalOnProperty(name = {"crypto.enabled"}, havingValue = "true")
    public CryptoPaymentService cryptoPaymentService() {
        return new CryptoPaymentService();
    }

    @Bean
    @ConditionalOnProperty(name = {"crypto.enabled"}, havingValue = "true")
    public CryptoConversionService cryptoConversionService() {
        return new CryptoConversionService();
    }
}

