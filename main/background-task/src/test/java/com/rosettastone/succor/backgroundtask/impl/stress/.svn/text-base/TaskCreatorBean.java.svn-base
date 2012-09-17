package com.rosettastone.succor.backgroundtask.impl.stress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.model.Task;

public class TaskCreatorBean {

    private final Log log = LogFactory.getLog(TaskCreatorBean.class);

    private TaskManager taskManager;

    public void createTestTasks(int taskId) {
        log.debug("Create new tasks");
        taskManager.createEmailTask(createTask(taskId));
        taskManager.createParatureTask(createTask(taskId));
    }

    private Task createTask(int i) {
        Task t = new Task();
        t.setRawArguments(new Object[] {"Test task " + i });
        t.setBeanName("echoBean");
        t.setMethodName("printEcho");
        return t;
    }

    @Required
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
}
