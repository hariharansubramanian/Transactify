package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.modernbank.exception.classes.InsufficientFundsException;
import dev.hari.playground.modernbank.exception.classes.InvalidAccountException;
import dev.hari.playground.modernbank.exception.classes.PaymentRequestValidationException;

/**
 * Service behaviors for dealing with payments
 */
public interface PaymentService {

    /**
     * Process a payment request between accounts
     *
     * @param request The payment request to process {@link PaymentRequest}
     */
    void processPayment(PaymentRequest request) throws PaymentRequestValidationException, InvalidAccountException, InsufficientFundsException, ExchangeRatesFetchException;
}
