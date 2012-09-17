package com.rosettastone.succor.backgroundtask;

import java.util.Map;

import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;

/**
 * Model bean for tasks statistic.
 */
public class TaskStatisticBean {

    private TaskType taskType;
    
    private Map<TaskStatus, Long> statistic;

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Map<TaskStatus, Long> getStatistic() {
        return statistic;
    }

    public void setStatistic(Map<TaskStatus, Long> statistic) {
        this.statistic = statistic;
    }
}
