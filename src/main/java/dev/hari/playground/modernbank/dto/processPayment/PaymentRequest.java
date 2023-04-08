package dev.hari.playground.modernbank.dto.processPayment;

import dev.hari.playground.modernbank.exception.PaymentRequestValidationException;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO contract for transferring money between accounts
 */
public class PaymentRequest {
    @Min(value = 1, message = "Source account ID must be greater than 0")
    public long sourceAccountId;

    @Min(value = 1, message = "Destination account ID must be greater than 0")
    public long destinationAccountId;

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    public BigDecimal amount;

    /**
     * Validates the payment request properties
     *
     * @throws PaymentRequestValidationException
     */
    public void Validate() throws PaymentRequestValidationException {
        List<String> errors = new ArrayList<>();

        if (sourceAccountId <= 0) {
            errors.add("Source account ID must be greater than 0");
        }

        if (destinationAccountId <= 0) {
            errors.add("Destination account ID must be greater than 0");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Amount must be greater than 0");
        }

        if (!errors.isEmpty()) {
            throw new PaymentRequestValidationException(errors);
        }
    }
}