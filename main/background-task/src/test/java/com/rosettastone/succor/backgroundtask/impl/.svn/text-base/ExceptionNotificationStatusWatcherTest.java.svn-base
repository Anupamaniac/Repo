package com.rosettastone.succor.backgroundtask.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskType;
import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.exception.ObjectNotFoundException;
import com.rosettastone.succor.exception.ParatureErrorCode;
import com.rosettastone.succor.exception.ParatureException;

@Test
public class ExceptionNotificationStatusWatcherTest {

    private ExceptionNotificationStatusWatcher watcher;

    @BeforeMethod
    public void init() {
        watcher = new ExceptionNotificationStatusWatcher();
    }

    public void testExeption() {
        Exception exception = new ApplicationException("Test");
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Assert.assertTrue(watcher.needNotify(task, exception));

        watcher.notificationSent(task, exception);

        Assert.assertEquals(watcher.needNotify(task, exception), false);
    }

    public void testExeptionDifferentTask() {
        Exception exception = new ApplicationException("Test");
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Assert.assertTrue(watcher.needNotify(task, exception));

        watcher.notificationSent(task, exception);

        task = new Task();
        task.setType(TaskType.PARATURE);
        Assert.assertEquals(watcher.needNotify(task, exception), false);
    }

    public void testClean() {
        Exception exception = new ApplicationException("Test");
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Assert.assertTrue(watcher.needNotify(task, exception));

        watcher.notificationSent(task, exception);
        watcher.cleanNotificationStatuses(task);

        Assert.assertTrue(watcher.needNotify(task, exception));
    }

    public void testCleanDifferentTask() {
        Exception exception = new ApplicationException("Test");
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Assert.assertTrue(watcher.needNotify(task, exception));

        watcher.notificationSent(task, exception);
        watcher.cleanNotificationStatuses(task);

        task = new Task();
        task.setType(TaskType.PARATURE);
        Assert.assertTrue(watcher.needNotify(task, exception));
    }

    public void testDifferentExeption() {
        Exception exception = new ApplicationException("Test");
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Assert.assertTrue(watcher.needNotify(task, exception));

        watcher.notificationSent(task, exception);

        exception = new RuntimeException("Test");
        Assert.assertTrue(watcher.needNotify(task, exception));
    }

    public void testDifferentExeptionDifferentTask() {
        Exception exception = new ApplicationException("Test");
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Assert.assertTrue(watcher.needNotify(task, exception));

        watcher.notificationSent(task, exception);

        exception = new RuntimeException("Test");
        task = new Task();
        task.setType(TaskType.PARATURE);
        Assert.assertTrue(watcher.needNotify(task, exception));
    }

    public void testCleanDifferentExeptionDifferentTask() {
        Task task = new Task();
        task.setType(TaskType.EMAIL);

        Exception exception = new ApplicationException("Test");
        watcher.notificationSent(task, exception);

        exception = new RuntimeException("Test");
        watcher.notificationSent(task, exception);

        exception = new IllegalArgumentException("Test");
        watcher.notificationSent(task, exception);

        Task task2 = new Task();
        task2.setType(TaskType.PARATURE);

        exception = new ApplicationException("Test");
        watcher.notificationSent(task2, exception);

        exception = new NullPointerException("Test");
        watcher.notificationSent(task2, exception);

        exception = new IllegalArgumentException("Test");
        watcher.notificationSent(task2, exception);

        watcher.cleanNotificationStatuses(task);

        Assert.assertTrue(watcher.needNotify(task2, exception));

        exception = new NullPointerException("Test");
        Assert.assertEquals(watcher.needNotify(task2, exception), false);
    }

    public void testTwoExeption() {
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Exception exception1 = new ApplicationException("Test");
        Assert.assertTrue(watcher.needNotify(task, exception1));

        watcher.notificationSent(task, exception1);

        Exception exception2 = new RuntimeException("Test");
        Assert.assertTrue(watcher.needNotify(task, exception2));
    }

    public void testParatureException404() {
        Task task = new Task();
        task.setType(TaskType.PARATURE);
        ParatureException exception = new ParatureException("Test", ParatureErrorCode.NOT_FOUND_404);

        watcher.notificationSent(task, exception);
        Assert.assertTrue(watcher.needNotify(task, exception));
    }

    public void testParatureException404Customer() {
        Task task = new Task();
        task.setType(TaskType.PARATURE);
        ParatureException exception = new ParatureException("Test", ParatureErrorCode.NOT_FOUND_404_CUSTOMER);

        watcher.notificationSent(task, exception);
        Assert.assertTrue(watcher.needNotify(task, exception));
    }

    public void testParatureUnavaliableException() {
        Task task = new Task();
        task.setType(TaskType.PARATURE);
        ParatureException exception = new ParatureException("Test", ParatureErrorCode.BAD_GATEWAY_502);

        watcher.notificationSent(task, exception);
        Assert.assertEquals(watcher.needNotify(task, exception), false);
    }

    public void testObjectNotFoundException() {
        Exception exception = new ObjectNotFoundException("Exception 1");
        Task task = new Task();
        task.setType(TaskType.EMAIL);
        Assert.assertTrue(watcher.needNotify(task, exception));

        watcher.notificationSent(task, exception);

        exception = new ObjectNotFoundException("Exception 2");
        Assert.assertTrue(watcher.needNotify(task, exception));
    }
}
