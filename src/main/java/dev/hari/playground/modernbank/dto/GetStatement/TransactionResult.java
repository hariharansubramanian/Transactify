package dev.hari.playground.modernbank.dto.GetStatement;

import dev.hari.playground.modernbank.model.Transaction;

import java.math.BigDecimal;

public class TransactionResult {
    public String type;
    public BigDecimal amount;
    public String currency;
    public String createdAt;

    public static TransactionResult fromEntity(Transaction transaction) {
        var result = new TransactionResult();
        result.type = transaction.type.name();
        result.amount = transaction.amount;
        result.currency = transaction.currency.getDisplayName();
        result.createdAt = transaction.createdAt.toString();
        return result;
    }
}
