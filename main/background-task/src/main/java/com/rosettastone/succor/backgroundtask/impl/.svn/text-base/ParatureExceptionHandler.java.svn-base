package com.rosettastone.succor.backgroundtask.impl;

import java.util.Map;

import com.rosettastone.succor.exception.ParatureErrorCode;
import com.rosettastone.succor.exception.ParatureException;
@Deprecated
public class ParatureExceptionHandler extends AbstractDelegatExceptionHandler {

    private Map<ParatureErrorCode, AbstractExceptionHandler> delegates;

    @Override
    protected AbstractExceptionHandler doFindDelegatExceptionHandler(Throwable exception) {
        if (exception instanceof ParatureException) {
            ParatureException paratureException = (ParatureException) exception;
            return delegates.get(paratureException.getErrorCode());
        }
        return null;
    }

    public void setDelegates(Map<ParatureErrorCode, AbstractExceptionHandler> delegates) {
        this.delegates = delegates;
    }

}
