package com.rosettastone.succor.backgroundtask;

import java.util.List;

import com.rosettastone.succor.backgroundtask.model.Task;

/**
 * Interface for task manager. It creates and schedules concrete task.
 * These fields in Task object are required:
 *  - rawArguments
 *  - beanName
 *  - methodName
 * If some of them were not defined than manager throws IllegalArgumentException.
 */
public interface TaskManager {

    void createParatureTask(Task task);

    void createEmailTask(Task task);

    void createPrepareTask(Task task);

    void createRules(Task task);

    void createSMSTask(Task task);

    void createDiscoveryCallTask(Task task);

    List<TaskStatisticBean> generateStatistic();
}
