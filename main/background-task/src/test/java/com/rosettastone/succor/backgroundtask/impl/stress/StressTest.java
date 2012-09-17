package com.rosettastone.succor.backgroundtask.impl.stress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.model.Task;

@Test (enabled = false)
@ContextConfiguration({"classpath:/backgroundtask-context.xml", "classpath:/stress-test-context.xml" })
public class StressTest extends AbstractTestNGSpringContextTests {

    private static final int TASK_AMOUNT = 10000;
    private static final int SLEEP_TIME = 60 * 60 * 1000;

    @Autowired
    private TaskManager taskManager;

    public void test() throws InterruptedException {
        for (int i = 0; i < TASK_AMOUNT; i++) {
            taskManager.createRules(createTask(i));
        }
        Thread.sleep(SLEEP_TIME);
    }

    private Task createTask(int i) {
        Task t = new Task();
        t.setRawArguments(new Object[] {i});
        t.setBeanName("taskCreatorBean");
        t.setMethodName("createTestTasks");
        return t;
    }

}
