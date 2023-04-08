package dev.hari.playground.modernbank.exception;

/**
 * Exception thrown when payment is not allowed
 */
public class PaymentNotAllowedException extends Exception {
    public PaymentNotAllowedException(String message) {
        super(message);
    }
}
