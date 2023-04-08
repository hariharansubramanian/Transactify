package dev.hari.playground.modernbank.service;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.PaymentRequestValidationException;

/**
 * Service behaviors for dealing with payments
 */
public interface PaymentService {

    /**
     * Process a payment request between accounts
     *
     * @param request The payment request to process {@link PaymentRequest}
     */
    void processPayment(PaymentRequest request) throws PaymentRequestValidationException;
}
