package com.rosettastone.succor.backgroundtask.impl;

import java.util.HashSet;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rosettastone.succor.backgroundtask.model.HistoryEntity;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;

@Test
public class ObjectNotFoundExceptionHandlerTest {

    private static final long RETRY_INTERVAL = 1000;
    private static final int RETRY_AMOUNT = 2;

    private ObjectNotFoundExceptionHandler handler;

    @BeforeMethod
    public void init() {
        handler = new ObjectNotFoundExceptionHandler();
        handler.setRetryInterval(RETRY_INTERVAL);
        handler.setRetryAmount(RETRY_AMOUNT);
    }

    public void testFirstOccured() {
        EmailSender mockSender = EasyMock.createStrictMock(EmailSender.class);
        EasyMock.replay(mockSender);
        handler.setEmailSender(mockSender);

        Task task = new Task();
        task.setHistory(new HashSet<HistoryEntity>());
        task.getHistory().add(new HistoryEntity());
        Exception exception = new Exception();
        handler.handleException(task, exception);

        Assert.assertNull(task.getStatus());
    }

    public void testSecondOccured() {
        EmailSender mockSender = EasyMock.createStrictMock(EmailSender.class);
        EasyMock.replay(mockSender);
        handler.setEmailSender(mockSender);

        Task task = new Task();
        task.setHistory(new HashSet<HistoryEntity>());
        task.getHistory().add(new HistoryEntity());
        task.getHistory().add(new HistoryEntity());
        Exception exception = new Exception();
        handler.handleException(task, exception);

        Assert.assertNull(task.getStatus());
    }

    public void testThirdOccured() throws Exception {
        Task task = new Task();
        task.setHistory(new HashSet<HistoryEntity>());
        task.getHistory().add(new HistoryEntity());
        task.getHistory().add(new HistoryEntity());
        task.getHistory().add(new HistoryEntity());
        Exception exception = new Exception();

        EmailSender mockSender = EasyMock.createStrictMock(EmailSender.class);
        mockSender.sendEmail(task, exception);
        EasyMock.replay(mockSender);
        handler.setEmailSender(mockSender);

        handler.handleException(task, exception);
        Assert.assertEquals(task.getStatus(), TaskStatus.CANCELLED);
    }
}
