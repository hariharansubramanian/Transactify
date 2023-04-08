package dev.hari.playground.modernbank.dto.getStatement;

import java.util.List;

public class GetStatementResult {
    public long accountId;
    public List<TransactionResult> transactions;
}

