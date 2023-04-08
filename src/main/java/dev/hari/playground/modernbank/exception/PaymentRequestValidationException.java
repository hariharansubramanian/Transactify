package dev.hari.playground.modernbank.exception;

import java.util.List;

/**
 * Exception thrown when there are validation errors in the payment request
 */
public class PaymentRequestValidationException extends Exception {
    public PaymentRequestValidationException(List<String> errors) {
        super(String.join(", ", errors));
    }
}
