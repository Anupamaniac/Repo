package com.rosettastone.succor.backgroundtask.impl;

import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;

@Test
public class TaskManagerImplTest {

    public void testCreateParatureTask() {
        Task task = createTask();
        TaskManager taskManager = createTaskManager(task);
        taskManager.createParatureTask(task);
        Assert.assertNotNull(task.getArguments());
        Assert.assertEquals(task.getStatus(), TaskStatus.ACTIVE);
        Assert.assertEquals(task.getType(), TaskType.PARATURE);
    }

    public void testCreateEmailTask() {
        Task task = createTask();
        TaskManager taskManager = createTaskManager(task);
        taskManager.createEmailTask(task);
        Assert.assertNotNull(task.getArguments());
        Assert.assertEquals(task.getStatus(), TaskStatus.ACTIVE);
        Assert.assertEquals(task.getType(), TaskType.EMAIL);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class })
    public void testCreateTaskNoMethod() {
        Task task = createTask();
        TaskManager taskManager = createTaskManager(task);
        task.setMethodName(null);
        taskManager.createEmailTask(task);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class })
    public void testCreateTaskNoBean() {
        Task task = createTask();
        TaskManager taskManager = createTaskManager(task);
        task.setBeanName(null);
        taskManager.createEmailTask(task);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class })
    public void testCreateTaskNoArguments() {
        Task task = createTask();
        TaskManager taskManager = createTaskManager(task);
        task.setRawArguments(null);
        taskManager.createEmailTask(task);
    }

    private TaskManager createTaskManager(Task task) {
        TaskManagerImpl taskManager = new TaskManagerImpl();
        taskManager.setArgumentSerializer(new ArgumentSerializer());
        taskManager.setCurrentRabbitMqMessageStorage(new ThreadLocal<Message>());
        TaskDao taskDao = EasyMock.createMock(TaskDao.class);
        taskDao.create(task);
        EasyMock.replay(taskDao);
        taskManager.setTaskDao(taskDao);
        return taskManager;
    }

    private Task createTask() {
        Task task = new Task();
        task.setBeanName("beanName");
        task.setMethodName("methodName");
        task.setRawArguments(new Object[]{});
        return task;
    }

}
