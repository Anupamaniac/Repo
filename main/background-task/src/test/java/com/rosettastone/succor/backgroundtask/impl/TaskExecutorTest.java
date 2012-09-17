package com.rosettastone.succor.backgroundtask.impl;

import java.util.Date;
import java.util.HashSet;

import org.easymock.EasyMock;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.backgroundtask.ExceptionHandler;
import com.rosettastone.succor.backgroundtask.model.HistoryEntity;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskExecutionStatus;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;

@Test
public class TaskExecutorTest {

    private static final long NO_TASK_DELAY = 60;

    private static final int DB_CLEAN_ITERATIONS = 150;

    public void testExecuteNoTask() {
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskType(TaskType.EMAIL);
        taskExecutor.setNoTaskDelay(NO_TASK_DELAY);

        TaskDao taskDao = EasyMock.createMock(TaskDao.class);
        taskDao.findFirstActiveTask(TaskType.EMAIL);
        EasyMock.expectLastCall().andReturn(null);
        taskDao.deleteOldTasks(EasyMock.anyObject(Date.class));
        taskExecutor.setTaskDao(taskDao);
        EasyMock.replay(taskDao);

		taskExecutor.setTransactionTemplate(new MockTransactionTemplate());

        long delay = taskExecutor.execute();
        Assert.assertEquals(delay, NO_TASK_DELAY * TaskExecutor.ONE_SECOND);
    }

    public void testExecute() {
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskType(TaskType.EMAIL);

        Task task = createTestTask();

        testExecutCreateMockObjects(taskExecutor, task);

        long delay = taskExecutor.execute();
        Assert.assertEquals(delay, 0);
        Assert.assertEquals(task.getStatus(), TaskStatus.COMPLETED);
        Assert.assertNull(task.getNextRun());
        Assert.assertEquals(task.getHistory().size(), 1);
        HistoryEntity he = task.getHistory().iterator().next();
        Assert.assertNull(he.getException());
        Assert.assertNull(he.getStacktrace());
        Assert.assertNotNull(he.getDate());
        Assert.assertEquals(he.getStatus(), TaskExecutionStatus.SUCCESS);
    }

    private Task createTestTask() {
        Task task = new Task();
        task.setArguments("");
        task.setBeanName("testBean");
        task.setMethodName("handleException");
        task.setHistory(new HashSet<HistoryEntity>());
        return task;
    }

    private void testExecutCreateMockObjects(TaskExecutor taskExecutor, Task task) {
        TaskDao taskDao = EasyMock.createMock(TaskDao.class);
        taskDao.findFirstActiveTask(TaskType.EMAIL);
        EasyMock.expectLastCall().andReturn(task);
        taskDao.update(task);
        taskDao.deleteOldTasks(EasyMock.anyObject(Date.class));
        EasyMock.replay(taskDao);
        taskExecutor.setTaskDao(taskDao);

        ArgumentSerializer serializer = EasyMock.createMock(ArgumentSerializer.class);
        serializer.deserialize(task.getArguments());
        Exception mockException = new Exception();
        EasyMock.expectLastCall().andReturn(new Object[]{task, mockException});
        EasyMock.replay(serializer);
        taskExecutor.setArgumentSerializer(serializer);

        ExceptionHandler mockBeanToInvoke = EasyMock.createMock(ExceptionHandler.class);
        mockBeanToInvoke.handleException(task, mockException);
        EasyMock.expectLastCall().andReturn(0);
        EasyMock.replay(mockBeanToInvoke);

        ApplicationContext applicationContext = EasyMock.createMock(ApplicationContext.class);
        applicationContext.getBean(task.getBeanName());
        EasyMock.expectLastCall().andReturn(mockBeanToInvoke);
        EasyMock.replay(applicationContext);
        taskExecutor.setApplicationContext(applicationContext);

		TransactionTemplate txTemplate = new MockTransactionTemplate();
        taskExecutor.setTransactionTemplate(txTemplate);

        ExceptionNotificationStatusWatcher watcher = EasyMock.createMock(ExceptionNotificationStatusWatcher.class);
        watcher.cleanNotificationStatuses(task);
        EasyMock.replay(watcher);
        taskExecutor.setExceptionNotificationStatusWatcher(watcher);

        taskExecutor.setCurrentRabbitMqMessageStorage(new ThreadLocal<Message>());
    }

    public void testExecuteError() {
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskType(TaskType.EMAIL);

        Task task = createTestTask();
        testExecuteErrorCreateMockObjects(taskExecutor, task);

        long delay = taskExecutor.execute();
        Assert.assertEquals(delay, 0);
        Assert.assertEquals(task.getStatus(), TaskStatus.ACTIVE);
        Assert.assertEquals(task.getHistory().size(), 1);
        HistoryEntity he = task.getHistory().iterator().next();
        Assert.assertNotNull(he.getException());
        Assert.assertNotNull(he.getStacktrace());
        Assert.assertNotNull(he.getDate());
        Assert.assertEquals(he.getStatus(), TaskExecutionStatus.FAILED);
    }

    private void testExecuteErrorCreateMockObjects(TaskExecutor taskExecutor, Task task) {
        TaskDao taskDao = EasyMock.createMock(TaskDao.class);
        taskDao.findFirstActiveTask(TaskType.EMAIL);
        EasyMock.expectLastCall().andReturn(task);
        taskDao.update(task);
        taskDao.deleteOldTasks(EasyMock.anyObject(Date.class));
        EasyMock.replay(taskDao);
        taskExecutor.setTaskDao(taskDao);

        ArgumentSerializer serializer = EasyMock.createMock(ArgumentSerializer.class);
        serializer.deserialize(task.getArguments());
        RuntimeException mockException = new RuntimeException();
        EasyMock.expectLastCall().andReturn(new Object[]{task, mockException});
        EasyMock.replay(serializer);
        taskExecutor.setArgumentSerializer(serializer);

        ExceptionHandler mockBeanToInvoke = EasyMock.createMock(ExceptionHandler.class);
        mockBeanToInvoke.handleException(task, mockException);
        EasyMock.expectLastCall().andThrow(mockException);
        EasyMock.replay(mockBeanToInvoke);

        ApplicationContext applicationContext = EasyMock.createMock(ApplicationContext.class);
        applicationContext.getBean(task.getBeanName());
        EasyMock.expectLastCall().andReturn(mockBeanToInvoke);
        EasyMock.replay(applicationContext);
        taskExecutor.setApplicationContext(applicationContext);

        ExceptionHandler exceptionHandler = EasyMock.createMock(ExceptionHandler.class);
        exceptionHandler.handleException(task, mockException);
        EasyMock.expectLastCall().andReturn(0);
        taskExecutor.setExceptionHandler(exceptionHandler);

        TransactionTemplate txTemplate = new MockTransactionTemplate();
        taskExecutor.setTransactionTemplate(txTemplate);

        taskExecutor.setCurrentRabbitMqMessageStorage(new ThreadLocal<Message>());
    }

    public void testDbCleaning() {
        TaskDao taskDao = EasyMock.createMock(TaskDao.class);
        taskDao.findFirstActiveTask(TaskType.EMAIL);
        EasyMock.expectLastCall().andReturn(null).times(DB_CLEAN_ITERATIONS);
        taskDao.deleteOldTasks(EasyMock.anyObject(Date.class));
        EasyMock.expectLastCall().times(2);
        EasyMock.replay(taskDao);

        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskType(TaskType.EMAIL);
        taskExecutor.setTaskDao(taskDao);
        taskExecutor.setTransactionTemplate(new MockTransactionTemplate());

        for (int i = 0; i < DB_CLEAN_ITERATIONS; i++) {
            taskExecutor.execute();
        }
    }
}
