package com.rosettastone.succor.backgroundtask.impl;

import java.util.Date;

import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;

public class ObjectNotFoundExceptionHandler extends RetryExceptionHandler {

    @Override
    public long calculateDelay(Task task, Throwable exception) {
        return NO_PAUSE_DELAY;
    }

    @Override
    public void updateTask(Task task, Throwable exception) {
        if (retryThresholdReached(task)) {
            task.setStatus(TaskStatus.CANCELLED);
            task.setNextRun(null);
        } else {
            long delay = super.calculateDelay(task, exception);
            task.setNextRun(new Date(System.currentTimeMillis() + delay));
        }
    }
}
