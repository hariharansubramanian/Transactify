package dev.hari.playground.modernbank.exception;

/**
 * Exception thrown when an account does not exist
 */
public class InvalidAccountException extends Exception {
    public InvalidAccountException(String message) {
        super(message);
    }
}
