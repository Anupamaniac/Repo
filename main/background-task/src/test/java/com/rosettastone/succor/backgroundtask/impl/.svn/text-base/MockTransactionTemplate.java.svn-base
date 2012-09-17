package com.rosettastone.succor.backgroundtask.impl;

import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class MockTransactionTemplate extends TransactionTemplate {
    @Override
    public <T> T execute(TransactionCallback<T> action) {
        return action.doInTransaction(null);
    }
}
