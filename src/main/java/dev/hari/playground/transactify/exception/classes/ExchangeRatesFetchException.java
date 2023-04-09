package dev.hari.playground.transactify.exception.classes;

/**
 * Exception thrown when the exchange rates could not be fetched
 */
public class ExchangeRatesFetchException extends Exception {

    public ExchangeRatesFetchException(String message) {
        super(message);
    }
}
