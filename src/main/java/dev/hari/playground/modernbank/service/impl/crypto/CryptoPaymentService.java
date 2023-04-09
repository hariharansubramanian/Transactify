package dev.hari.playground.modernbank.service.impl.crypto;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.modernbank.exception.classes.InsufficientFundsException;
import dev.hari.playground.modernbank.exception.classes.InvalidAccountException;
import dev.hari.playground.modernbank.exception.classes.PaymentRequestValidationException;
import dev.hari.playground.modernbank.service.PaymentService;
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
