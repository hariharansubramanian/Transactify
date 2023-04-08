package dev.hari.playground.modernbank.service.impl;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.PaymentRequestValidationException;
import dev.hari.playground.modernbank.service.PaymentService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

/**
 * Bank specific implementations of {@link PaymentService} behaviors for payments
 */
@Service
public class BankPaymentService implements PaymentService {
    @Override
    public void processPayment(PaymentRequest request) throws PaymentRequestValidationException {
        // Validate the request
        request.Validate();

        // TODO: Implement payment transfer logic
        throw new NotImplementedException("Not implemented yet");
    }
}
