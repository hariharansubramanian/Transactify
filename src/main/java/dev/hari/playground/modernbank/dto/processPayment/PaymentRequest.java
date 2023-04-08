package dev.hari.playground.modernbank.dto.processPayment;

import java.math.BigDecimal;

/**
 * DTO contract for transferring money between accounts
 */
public class PaymentRequest {
    public long sourceAccountId;
    public long destinationAccountId;
    public BigDecimal amount;
}
