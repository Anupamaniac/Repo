package com.rosettastone.succor.backgroundtask;

import com.rosettastone.succor.backgroundtask.model.Task;

/**
 * Base interface for exception handler.
 * Exception handler can modify task object (for example cancel task)
 */

public interface ExceptionHandler {

    public static long NO_PAUSE_DELAY = 0;
    
    /**
     * 
     * @param task - current running task
     * @param throwable - exception that was thrown by task executor while it runs the task
     * @return Thread execution delay. 0 means that thread should not pause execution.
     */
    long handleException(Task task, Throwable throwable);
}
