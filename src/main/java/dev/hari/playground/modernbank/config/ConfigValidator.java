package dev.hari.playground.modernbank.config;

import dev.hari.playground.modernbank.exception.AppConfigurationException;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Validate properties in application.properties making sure that they are mutually exclusive or required
 * Throw {@link AppConfigurationException} if any property is invalid
 */
@Configuration
public class ConfigValidator {

    private final Environment env;

    public ConfigValidator(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void validateProperties() throws AppConfigurationException {
        validateMutuallyExclusive("bank.enabled", "crypto.enabled");
        validateMutuallyExclusive("fileExchange.enabled", "apiExchange.enabled");
        validateMutuallyExclusive("inMemoryDb.enabled", "jpa.enabled");
    }

    private void validateMutuallyExclusive(String property1, String property2) throws AppConfigurationException {
        boolean prop1Value = env.getProperty(property1, Boolean.class, false);
        boolean prop2Value = env.getProperty(property2, Boolean.class, false);

        if (prop1Value == prop2Value) {
            throw new AppConfigurationException("Properties " + property1 + " and " + property2 + " must be mutually exclusive.");
        }
    }
}