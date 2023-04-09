package dev.hari.playground.transactify.service;

import dev.hari.playground.transactify.dto.processPayment.PaymentRequest;
import dev.hari.playground.transactify.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.transactify.exception.classes.InsufficientFundsException;
import dev.hari.playground.transactify.exception.classes.InvalidAccountException;
import dev.hari.playground.transactify.exception.classes.PaymentRequestValidationException;

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
