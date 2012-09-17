package com.rosettastone.succor.keepalive;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.TaskStatisticBean;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.mdp.RabbitMqChannelManager;
import com.rosettastone.succor.mdp.RabbitMqListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * The {@code StatisticController} provides ability to view some statistic info.
 */
@Controller
public class StatisticController {

    @Autowired
    private RabbitMqListener rabbitMqListener;

    @Autowired
    private TaskManager taskManager;

    /**
     * Provides statistic info.
     *
     * @param request
     * @param pr
     */
    @RequestMapping("/statistic")
    public void getMessageCount(HttpServletRequest request, PrintWriter pr) {
        int count = rabbitMqListener.getMessageCount();
        pr.println( "Number of the messages in the queue: " + count );

        StringBuilder buffer = new StringBuilder();
        List<TaskStatisticBean> beans = taskManager.generateStatistic();
        long activeTotal = 0;
        long completedTotal = 0;
        long cancelledTotal = 0;
        for (TaskStatisticBean taskStatisticBean : beans) {
            long active = getPrimitiveValue(taskStatisticBean.getStatistic().get(TaskStatus.ACTIVE));
            long completed = getPrimitiveValue(taskStatisticBean.getStatistic().get(TaskStatus.COMPLETED));
            long cancelled = getPrimitiveValue(taskStatisticBean.getStatistic().get(TaskStatus.CANCELLED));
            activeTotal += active;
            completedTotal += completed;
            cancelledTotal += cancelled;
        }
        pr.println( "\nRecords in the  DB  with  Active state: " + activeTotal);
        pr.println( "Records in the DB with Completed state: " + completedTotal);
        pr.println( "Records in the DB with Cancelled state: " + cancelledTotal);

    }

    private long getPrimitiveValue(Long value) {
        if (value == null) {
            return 0;
        }
        return value.longValue();
    }
}
