package com.rosettastone.succor.backgroundtask.impl;

import java.util.Map;

import com.rosettastone.succor.backgroundtask.ExceptionHandler;
import com.rosettastone.succor.exception.ParatureException;

public class RulesExceptionHandler extends AbstractDelegatExceptionHandler {

    private Map<String, ExceptionHandler> delegates;

    @Override
    protected ExceptionHandler doFindDelegatExceptionHandler(Throwable exception) {
        if (exception instanceof ParatureException) {
            ParatureException paratureException = (ParatureException) exception;
            return delegates.get(paratureException.getErrorCode().toString());
        } else {
            return delegates.get(exception.getClass().getName());
        }
    }

    public void setDelegates(Map<String, ExceptionHandler> delegates) {
        this.delegates = delegates;
    }
}
