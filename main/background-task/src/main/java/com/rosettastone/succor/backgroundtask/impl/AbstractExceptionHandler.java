package com.rosettastone.succor.backgroundtask.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.ExceptionHandler;
import com.rosettastone.succor.backgroundtask.model.Task;

public abstract class AbstractExceptionHandler implements ExceptionHandler {

    private static final Log LOG = LogFactory.getLog(AbstractExceptionHandler.class);

    private long errorDelay;

    @Override
    public final long handleException(Task task, Throwable exception) {
        try {
            doHandleException(task, exception);
        } catch (Exception e) {
				// This looks like eating exception but the exception will never occur in above block as this method is overriden by subclass which is handling exception
				e.printStackTrace();
			}
            updateTask(task, exception);
            return calculateDelay(task, exception);
    }

    protected abstract void doHandleException(Task task, Throwable exception) throws Exception;

    protected long calculateDelay(Task task, Throwable exception) {
        return errorDelay;
    }

    protected void updateTask(Task task, Throwable exception) {
        //Do nothing by default
    }

    @Required
    public void setErrorDelay(long errorDelay) {
        this.errorDelay = errorDelay * TaskExecutor.ONE_SECOND;
    }

    protected long getErrorDelay() {
        return errorDelay;
    }
}
