package dev.hari.playground.transactify.service.impl.crypto;

import dev.hari.playground.transactify.dto.processPayment.PaymentRequest;
import dev.hari.playground.transactify.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.transactify.exception.classes.InsufficientFundsException;
import dev.hari.playground.transactify.exception.classes.InvalidAccountException;
import dev.hari.playground.transactify.exception.classes.PaymentRequestValidationException;
import dev.hari.playground.transactify.service.PaymentService;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Cryptocurrency specific implementation of {@link PaymentService}
 */
public class CryptoPaymentService implements PaymentService {
    @Override
    public void processPayment(PaymentRequest request) throws PaymentRequestValidationException, InvalidAccountException, InsufficientFundsException, ExchangeRatesFetchException {
        throw new NotImplementedException("process payment for Crypto not implemented yet");
    }
}
