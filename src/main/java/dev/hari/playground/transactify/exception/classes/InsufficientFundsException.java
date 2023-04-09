package dev.hari.playground.transactify.exception.classes;

/**
 * Exception thrown when there are insufficient funds in the source account to make a payment
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
