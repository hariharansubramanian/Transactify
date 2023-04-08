package dev.hari.playground.modernbank.dto.getStatement;

import dev.hari.playground.modernbank.model.Account;
import dev.hari.playground.modernbank.model.Transaction;

import java.util.List;

/**
 * DTO contract returned as a response to GET API /account/{id}/statement
 */
public class GetStatementResult {
    public long accountId;
    public List<TransactionResult> transactions;

    /**
     * Factory method to create DTO from {@link Account} entity and list of {@link Transaction}
     */
    public static GetStatementResult fromEntity(Account account, List<Transaction> transactions) {
        var result = new GetStatementResult();

        result.accountId = account.id;
        result.transactions = transactions
                .stream()
                .map(TransactionResult::fromEntity)
                .toList();

        return result;
    }
}

