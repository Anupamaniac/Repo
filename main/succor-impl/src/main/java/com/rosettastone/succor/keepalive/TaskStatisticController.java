package com.rosettastone.succor.keepalive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.TaskStatisticBean;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;

/**
 * The {@code TaskStatisticController} provides the ability to get the task statistic info.
 */

@Controller
public class TaskStatisticController {

    @Autowired
    private TaskManager taskManager;

    /**
     * Provides the task statistic info.
     * @return string
     */
    @RequestMapping("/taskstatistic")
    @ResponseBody
    public String statistic() {
        StringBuilder buffer = new StringBuilder();
        List<TaskStatisticBean> beans = taskManager.generateStatistic();
        long activeTotal = 0;
        long completedTotal = 0;
        long cancelledTotal = 0;
        buffer.append("Task type    |   Active  |   Completed   |   Cancelled\n");
        buffer.append("------------------------------------------------------\n");
        for (TaskStatisticBean taskStatisticBean : beans) {
            long active = getPrimitiveValue(taskStatisticBean.getStatistic().get(TaskStatus.ACTIVE));
            long completed = getPrimitiveValue(taskStatisticBean.getStatistic().get(TaskStatus.COMPLETED));
            long cancelled = getPrimitiveValue(taskStatisticBean.getStatistic().get(TaskStatus.CANCELLED));
            activeTotal += active;
            completedTotal += completed;
            cancelledTotal += cancelled;
            buffer.append(String.format("%s   |   %d      |       %d      |   %d       \n",
                    taskStatisticBean.getTaskType(), active, completed, cancelled));
        }
        buffer.append("------------------------------------------------------\n");
        buffer.append(String.format("Total:       |   %d      |       %d      |   %d       \n", activeTotal,
                completedTotal, cancelledTotal));
        return buffer.toString();
    }

    private long getPrimitiveValue(Long value) {
        if (value == null) {
            return 0;
        }
        return value.longValue();
    }
}
