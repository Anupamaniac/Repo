package com.rosettastone.succor.backgroundtask.impl.stress;

import com.rosettastone.succor.backgroundtask.MessageStatistic;
import com.rosettastone.succor.backgroundtask.impl.StatisticDao;
import com.rosettastone.succor.backgroundtask.impl.TaskDao;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.Calendar;
import java.util.Date;

@Test (enabled = false)
@ContextConfiguration({"classpath:/backgroundtask-context.xml", "classpath:/test-context.xml"})
public class StatisticDaoStressTest extends AbstractTestNGSpringContextTests {

    private static final Log logger = LogFactory.getLog(StatisticDaoStressTest.class);

    @Autowired
    private StatisticDao statisticDao;

    @Autowired
    private DataSource taskDataSource;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private HibernateTemplate taskHibernateTemplate;

    public void testLoadMessagesStatistic() {
        long startTime;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 06, 01, 00, 00, 00);
        startTime = System.currentTimeMillis();
        MessageStatistic messageStatistic = statisticDao.loadMessageStatistic(calendar.getTime());
        long delta = System.currentTimeMillis() - startTime;
        logger.info("Calculate statistic for messages: " + delta + " milliseconds");
        Assert.assertNotNull(messageStatistic);
        Assert.assertEquals(messageStatistic.getCompleted(), 3334l);
        Assert.assertEquals(messageStatistic.getCancelled(), 3333l);
        Assert.assertEquals(messageStatistic.getTotal(), 3333l);
        Assert.assertEquals(messageStatistic.getActive(), 10000l);
    }

    private void generateTestData() {
        logger.debug("Creating test messages");
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            Message message = new Message();
            message.setMessage("message" + i);
            taskDao.createMessage(message);
            for (int j = 0; j < 5; j++) {
                Task task = new Task();
                task.setMessage(message);
                task.setBeanName("bean");
                task.setMethodName("method");
                task.setRawArguments(new String[]{"a, b, c"});
                if (i % 3 == 0) {
                    task.setStatus(TaskStatus.COMPLETED);
                } else if (i % 3 == 1) {
                    task.setStatus(TaskStatus.ACTIVE);
                } else if (i % 3 == 2) {
                    task.setStatus(TaskStatus.CANCELLED);
                }

                switch (j) {
                    case 0:
                        task.setType(TaskType.RABBIT_MQ);
                        break;
                    case 1:
                        task.setType(TaskType.EMAIL);
                        break;
                    case 2:
                        task.setType(TaskType.PARATURE);
                        break;
                    case 3:
                        task.setType(TaskType.RULES);
                        break;
                    case 4:
                        task.setType(TaskType.DISCOVERY_CALL);
                        break;
                }
                taskDao.create(task);
            }
        }
        logger.info("Generating test messages time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
        logger.debug("End creating test messages");
    }

}
