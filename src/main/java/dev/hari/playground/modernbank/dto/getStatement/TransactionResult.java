package dev.hari.playground.modernbank.dto.getStatement;

import dev.hari.playground.modernbank.model.Transaction;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionResult {
    public String type;
    public BigDecimal amount;
    public String currency;
    public String createdAt;

    public static TransactionResult fromEntity(Transaction transaction) {
        var result = new TransactionResult();
        result.type = transaction.type.name();
        result.amount = transaction.getAmount();
        result.currency = transaction.currency.getCurrencyCode();
        result.createdAt = transaction.getCreatedAt().toString();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionResult that = (TransactionResult) o;

        if (!Objects.equals(type, that.type)) return false;
        if (!Objects.equals(amount, that.amount)) return false;
        if (!Objects.equals(currency, that.currency)) return false;
        return Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
