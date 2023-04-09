package dev.hari.playground.transactify.exception.classes;

/**
 * Exception thrown when there is an error in the configuration of the application
 */
public class AppConfigurationException extends Exception {
    public AppConfigurationException(String message) {
        super(message);
    }
}