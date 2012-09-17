package com.rosettastone.succor.backgroundtask.impl;

import com.rosettastone.succor.backgroundtask.ExceptionHandler;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Exception handler that just cancel task.
 */
public class EaterExceptionHandler implements ExceptionHandler {

    private final Log log = LogFactory.getLog(EaterExceptionHandler.class);

    @Override
    public long handleException(Task task, Throwable t) {
        log.error("Hanlde exception, task:" + task.getType() + "#" + task.getId());
        task.setStatus(TaskStatus.CANCELLED);
        task.setNextRun(null);
        return NO_PAUSE_DELAY;
    }

}
