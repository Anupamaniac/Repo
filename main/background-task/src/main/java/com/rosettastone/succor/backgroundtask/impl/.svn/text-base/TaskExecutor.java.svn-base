package com.rosettastone.succor.backgroundtask.impl;

import java.lang.reflect.Method;
import java.util.Date;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.exception.ParatureErrorCode;
import com.rosettastone.succor.exception.ParatureException;
import com.rosettastone.succor.utils.MessageUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.rosettastone.succor.backgroundtask.ExceptionHandler;
import com.rosettastone.succor.backgroundtask.model.HistoryEntity;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskExecutionStatus;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.service.HoptoadNotificationService;
import com.rosettastone.succor.spring.BanditEventScopeEvent;
import com.rosettastone.succor.spring.BanditEventScopeEvent.EventType;

/**
 * This is object that control loading and processing of task and save report to DB after execution.
 * TaskExecutor can process only one type of tasks.
 *
 * @see TaskExecutorThread
 * @see TaskType
 */
public class TaskExecutor extends ApplicationObjectSupport {

    public static final long ONE_SECOND = 1000;
    public static final long TEN_SECONDS = ONE_SECOND * 10;
    public static final long ONE_MONTH = 31 * 24 * 60 * 60 * 1000;
    public static final int DB_CLEANING_FREQUENCY = 100;

    private static final Log LOG = LogFactory.getLog(TaskExecutor.class);

    public static final int MAX_TASK_DELAY = 4 * 60;

    private TaskDao taskDao;

    /**
     * Type of task that will be loaded and processed by this executor.
     */
    private TaskType taskType;
    /**
     * It is handler for execptions that can be thrown
     */
    private ExceptionHandler exceptionHandler;
    private ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher;

    /**
     * Arguments serializer for model objects deserialization.
     */
    private ArgumentSerializer argumentSerializer;
    private TransactionTemplate transactionTemplate;
    private ThreadLocal<Message> currentRabbitMqMessageStorage;
    private ThreadLocal<Task> currentTask;
    private HoptoadNotificationService hoptoadNotificationService;
   

	private long noTaskDelay;

    private int executionCounter = 0;

    public long execute() {
        long delay = TEN_SECONDS;
        try {
//        	throw new Exception("Succor test error from development environment");
            delay = transactionTemplate.execute(new TransactionCallback<Long>() {
                @Override
                public Long doInTransaction(TransactionStatus status) {
                    final Task task = taskDao.findFirstActiveTask(taskType);
                    Long result;
                    if (task != null) {
                        result = executeTask(task);
                    } else {
                        result = noTaskDelay;
                    }
                    return result;
                }
            });
        } catch (Exception e) {
        	if(hoptoadNotificationService!=null)
        	hoptoadNotificationService.notifyingHopToad(e);
            LOG.info(e);
        }
//        cleanDb();
        return delay;
    }

    private long executeTask(Task task) {
        long delay = 0;
        try {
            currentRabbitMqMessageStorage.set(task.getMessage());
            currentTask.set(task);
            doExecute(task);
        } catch (Throwable t) {
        	 if ((task != null) && (task.getMessage() != null))
        	        hoptoadNotificationService.notifyingHopToad(task.getMessage().getMessage(), t);
        	      else {
        	hoptoadNotificationService.notifyingHopToad(t);
        	      }
            delay = catchExecutionException(task, t);
        } finally {
            currentRabbitMqMessageStorage.remove();
            currentTask.remove();
        }
        updateDbTask(task);
        return delay;
    }

    private void doExecute(Task task) {
        invokeBean(task);

        task.setNextRun(null);
        task.setStatus(TaskStatus.COMPLETED);

        HistoryEntity history = new HistoryEntity();
        history.setDate(new Date());
        history.setStatus(TaskExecutionStatus.SUCCESS);

        task.getHistory().add(history);
        exceptionNotificationStatusWatcher.cleanNotificationStatuses(task);
    }

    private void invokeBean(Task task) {
        Object[] arguments = argumentSerializer.deserialize(task.getArguments());
        task.setRawArguments(arguments);

        Object targetBean = getApplicationContext().getBean(task.getBeanName());
        Assert.notNull(targetBean);

        Method targetMethod = findMethod(targetBean.getClass(), task.getMethodName());
        Assert.notNull(targetMethod);

        invokeMethod(targetMethod, targetBean, arguments);
    }

    /*
     * Special method that initialize and destroy custom Spring's (CustomerScope) scope around invoke method.
     */
    private void invokeMethod(Method targetMethod, Object targetBean, Object[] arguments) {
        Event event = findEvent(arguments);
        // check that message is actual for current moment.
        if ((event != null) && !MessageUtils.isActual(event.getMessage())) {
            logger.info("Message "  + event.getMessage().getGuid() + " is not actual - skipping.");
            return;
        }
        if (event != null) {
            getApplicationContext().publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_STARTED));
        }
        try {
            ReflectionUtils.invokeMethod(targetMethod, targetBean, arguments);
        } finally {
            if (event != null) {
                getApplicationContext().publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_FINISHED));
            }
        }
    }

    /**
     * Find and return Event object from list of arguments.
     * @param arguments
     * @return event object or null
     */
    private Event findEvent(Object[] arguments) {
        for (Object object : arguments) {
            if ((object != null) && (object instanceof Event)) {
                return (Event) object;
            }
        }
        return null;
    }

    private Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods;
            if (searchType.isInterface()) {
                methods = searchType.getMethods();
            } else {
                methods = searchType.getDeclaredMethods();
            }
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private long catchExecutionException(Task task, Throwable t) {
        long delay;
        if ((t instanceof ParatureException)
                && (((ParatureException) t).getErrorCode() == ParatureErrorCode.NOT_FOUND_404_CUSTOMER)) {
            LOG.error("Customer with guid " + ((ParatureException) t).getObjectId() + " not found.");
        } else {
            LOG.error("Catch exception", t);
        }

        task.setStatus(TaskStatus.ACTIVE);

        HistoryEntity history = new HistoryEntity();
        history.setDate(new Date());
        history.setStatus(TaskExecutionStatus.FAILED);
        history.setException(t.getClass() + " : " + t.getMessage());
        history.setStacktrace(Util.stacktraceToString(t));

        task.getHistory().add(history);

        delay = exceptionHandler.handleException(task, t);
        updateMessageTasks(task);

        if (task.getStatus().equals(TaskStatus.ACTIVE)) {
            HistoryEntity firstRunHistory = taskDao.loadFirstHistory(task);
            if (firstRunHistory != null) {
                // cancel task if first run and last run date diff more than 4 hours
                if (dateDiffInMinutes(history.getDate(), firstRunHistory.getDate()) > MAX_TASK_DELAY) {
                    logger.debug("Time to complete the task has exprired:" + task.getId());
                    cancelTask(task);
                    // cancel task if it runs more than 50
                } else if (task.getHistory().size() > 50) {
                    logger.debug("The number of attempts for task is exceeded:" + task.getId());
                    cancelTask(task);
                } else {
                    // check time for rerunning task
                    Date afterMinute = new Date(System.currentTimeMillis() + DateUtils.MILLIS_PER_MINUTE);
                    if (task.getNextRun() == null || task.getNextRun().before(afterMinute)) {
                        logger.debug("Set next run time for task:" + task.getId());
                        task.setNextRun(new Date(System.currentTimeMillis() + DateUtils.MILLIS_PER_HOUR));
                        updateDbTask(task);
                    }
                }
            }
        }

        return delay;
    }

    private void cancelTask(Task task) {
        logger.debug("Cancelling task " + task.getId());
        task.setNextRun(null);
        task.setStatus(TaskStatus.CANCELLED);
        updateDbTask(task);
    }

    private long dateDiffInMinutes(Date first, Date second) {
        return (first.getTime() - second.getTime()) / DateUtils.MILLIS_PER_MINUTE;
    }

    private void updateDbTask(Task task) {
        try {
            taskDao.update(task);
        } catch (Exception e) {
        	if ((task != null) && (task.getMessage() != null))
                hoptoadNotificationService.notifyingHopToad(task.getMessage().getMessage(), e);
              else {
			hoptoadNotificationService.notifyingHopToad(e);
              }
            LOG.error("Can not update task", e);
        }
    }

   /* private void cleanDb() {
        if (executionCounter % DB_CLEANING_FREQUENCY == 0) {
            try {

                taskDao.deleteOldTasks(DateUtils.addMonths(new Date(), -1), taskType);
            } catch (Exception e) {
                LOG.error("Can not delete old tasks from DB", e);
            }
        }
        executionCounter++;
    }*/

    private void updateMessageTasks(Task task) {
        Message message = task.getMessage();
        if (message == null) {
            return;
        }
        if (TaskStatus.CANCELLED.equals(task.getStatus())) {
            cancelMessageTasks(task, message);
        }
        if (TaskStatus.ACTIVE.equals(task.getStatus())) {
            suspendMessageTasks(task, message);
        }
    }

    private void cancelMessageTasks(Task task, Message message) {
        for (Task msgTask : message.getTasks()) {
            if (msgTask.getType().equals(task.getType()) && !msgTask.getId().equals(task.getId())) {
                msgTask.setStatus(TaskStatus.CANCELLED);
                msgTask.setNextRun(null);
                updateDbTask(msgTask);
            }
        }
    }

    private void suspendMessageTasks(Task task, Message message) {
        for (Task msgTask : message.getTasks()) {
            if (msgTask.getType().equals(task.getType()) && !msgTask.getId().equals(task.getId())
                    && TaskStatus.ACTIVE.equals(msgTask.getStatus())) {
                msgTask.setNextRun(task.getNextRun());
                updateDbTask(msgTask);
            }
        }
    }

    @Required
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Required
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Required
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Required
    public void setArgumentSerializer(ArgumentSerializer argumentSerializer) {
        this.argumentSerializer = argumentSerializer;
    }

    @Required
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Required
    public void setExceptionNotificationStatusWatcher(
            ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher) {
        this.exceptionNotificationStatusWatcher = exceptionNotificationStatusWatcher;
    }

    /**
     * Set value in seconds.
     */
    @Required
    public void setNoTaskDelay(long noTaskDelay) {
        this.noTaskDelay = noTaskDelay * ONE_SECOND;
    }

    @Required
    public void setCurrentRabbitMqMessageStorage(ThreadLocal<Message> currentRabbitMqMessageStorage) {
        this.currentRabbitMqMessageStorage = currentRabbitMqMessageStorage;
    }


	public void setCurrentTask(ThreadLocal<Task> currentTask) {
		this.currentTask = currentTask;
	}

	public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}

	public void setHoptoadNotificationService(
			HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}
}
