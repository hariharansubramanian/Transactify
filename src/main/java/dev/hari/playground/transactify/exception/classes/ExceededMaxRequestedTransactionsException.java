package dev.hari.playground.transactify.exception.classes;

/**
 * Exception thrown when the requested number of transactions exceeds the maximum allowed for a statement
 */
public class ExceededMaxRequestedTransactionsException extends Exception{
    public ExceededMaxRequestedTransactionsException(String message) {
        super(message);
    }
}
