package com.rosettastone.succor.backgroundtask.impl.stress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rosettastone.succor.backgroundtask.ExceptionHandler;
import com.rosettastone.succor.backgroundtask.model.Task;

public class LogExceptionHandler implements ExceptionHandler {

    private final Log log = LogFactory.getLog(EchoBean.class);

    @Override
    public long handleException(Task task, Throwable t) {
        log.error("Hanlde exception, task:" + task.getType() + "#" + task.getId(), t);
        return NO_PAUSE_DELAY;
    }

}
