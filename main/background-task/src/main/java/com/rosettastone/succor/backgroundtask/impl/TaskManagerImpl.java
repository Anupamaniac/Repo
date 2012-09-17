package com.rosettastone.succor.backgroundtask.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.TaskStatisticBean;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;

/**
* Implementation of TaskManager interface
* The main purpose of manager is to serialize task and store to DB.
*/
public class TaskManagerImpl implements TaskManager {

    private ArgumentSerializer argumentSerializer;
    private TaskDao taskDao;
    private ThreadLocal<Message> currentRabbitMqMessageStorage;

    @Override
    public void createParatureTask(Task task) {
        createTask(task, TaskType.PARATURE);
    }

    @Override
    public void createEmailTask(Task task) {
        createTask(task, TaskType.EMAIL);
    }

    @Override
    public void createPrepareTask(Task task) {
        createMessage(task.getMessage());
        createTask(task, TaskType.PREPARE);
    }


    /**
     * Creates message and 'rules'' task.
     *
     * @param task
     */
    @Override
    public void createRules(Task task) {
        createTask(task, TaskType.RULES);
    }

    @Override
    public void createSMSTask(Task task) {
        createTask(task, TaskType.SMS);
    }

    @Override
    public void createDiscoveryCallTask(Task task) {
        createMessage(task.getMessage());
        checkBeforeCreate(task);
        createTask(task, TaskType.DISCOVERY_CALL);
    }

    private void createMessage(Message message) {
        Assert.notNull(message);
        Assert.notNull(message.getMessage());
        taskDao.createMessage(message);
    }

    private void createTask(Task task, TaskType type) {
        checkBeforeCreate(task);
        initCommonTaskArguments(task);
        task.setType(type);
        taskDao.create(task);
    }

    private void checkBeforeCreate(Task task) {
        Assert.notNull(task);
        Assert.notNull(task.getRawArguments());
        Assert.hasText(task.getBeanName());
        Assert.hasText(task.getMethodName());
    }

    private void initCommonTaskArguments(Task task) {
        task.setId(null);
        task.setHistory(null);
        task.setNextRun(null);
        task.setArguments(argumentSerializer.serialize(task.getRawArguments()));
        task.setStatus(TaskStatus.ACTIVE);
        Message currentMessage = currentRabbitMqMessageStorage.get();
        if (currentMessage != null) {
            task.setMessage(currentMessage);
        }
    }

    @Override
    public List<TaskStatisticBean> generateStatistic() {
        return taskDao.loadStatistic();
    }

    @Required
    public void setArgumentSerializer(ArgumentSerializer argumentSerializer) {
        this.argumentSerializer = argumentSerializer;
    }

    @Required
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Required
    public void setCurrentRabbitMqMessageStorage(ThreadLocal<Message> currentRabbitMqMessageStorage) {
        this.currentRabbitMqMessageStorage = currentRabbitMqMessageStorage;
    }
}
