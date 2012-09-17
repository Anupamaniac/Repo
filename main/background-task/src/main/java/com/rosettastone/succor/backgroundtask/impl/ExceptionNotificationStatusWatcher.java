package com.rosettastone.succor.backgroundtask.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskType;
import com.rosettastone.succor.exception.ObjectNotFoundException;
import com.rosettastone.succor.exception.ParatureErrorCode;
import com.rosettastone.succor.exception.ParatureException;

public class ExceptionNotificationStatusWatcher {

    private Map<TaskType, Set<Class<? extends Throwable>>> watchedExceptions
        = new HashMap<TaskType, Set<Class<? extends Throwable>>>();

    public ExceptionNotificationStatusWatcher() {
        for (TaskType type : TaskType.values()) {
            watchedExceptions.put(type, new HashSet<Class<? extends Throwable>>());
        }
    }

    public synchronized boolean needNotify(Task task, Throwable exception) {
        if (forceNotifyParatureException(exception) || forceNotifyApplicationException(exception)) {
            return true;
        }
        Class<? extends Throwable> exClazz = exception.getClass();
        for (TaskType type : TaskType.values()) {
            if (watchedExceptions.get(type).contains(exClazz)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void notificationSent(Task task, Throwable exception) {
        watchedExceptions.get(task.getType()).add(exception.getClass());
    }

    public synchronized void cleanNotificationStatuses(Task task) {
        List<Class<? extends Throwable>> cleanedExceptions = new ArrayList<Class<? extends Throwable>>();
        cleanedExceptions.addAll(watchedExceptions.get(task.getType()));
        watchedExceptions.get(task.getType()).clear();
        for (TaskType type : TaskType.values()) {
            watchedExceptions.get(type).removeAll(cleanedExceptions);
        }
    }

    public synchronized boolean hashExceptions() {
        for (TaskType type : TaskType.values()) {
            if (!watchedExceptions.get(type).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean forceNotifyParatureException(Throwable exception) {
        if (exception instanceof ParatureException) {
            ParatureException ex = (ParatureException) exception;
            return ParatureErrorCode.NOT_FOUND_404_CUSTOMER.equals(ex.getErrorCode())
                || ParatureErrorCode.NOT_FOUND_404.equals(ex.getErrorCode());
        }
        return false;
    }

    private boolean forceNotifyApplicationException(Throwable exception) {
        return exception instanceof ObjectNotFoundException;
    }
}
