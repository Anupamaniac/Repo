package com.rosettastone.succor.backgroundtask.model;

/**
 * Task type
 * There are separate executors for tasks with different type.
 * @see Task
 * @see com.rosettastone.succor.backgroundtask.impl.TaskExecutor
 */
public enum TaskType {
    PARATURE, EMAIL, RULES, RABBIT_MQ, SMS, FTP, DISCOVERY_CALL, PREPARE
}
