package com.rosettastone.succor.backgroundtask.impl;

import com.rosettastone.succor.TestUtil;
import com.rosettastone.succor.backgroundtask.MessageStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.annotation.NotTransactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.Calendar;

@Test(enabled = false)
@ContextConfiguration({ "classpath:/backgroundtask-context.xml", "classpath:/test-context.xml" })
@TransactionConfiguration(transactionManager = "taskTransactionManager")
public class StatisticDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private StatisticDao statisticDao;

    @Autowired
    private DataSource taskDataSource;

    @Autowired
    private HibernateTemplate taskHibernateTemplate;

    @BeforeMethod
    public void initDb() throws Exception {
        TestUtil.initDb("classpath:/statistic-dataset.xml", taskDataSource, applicationContext);
    }

    public void testConfiguration() {
        Assert.assertNotNull(statisticDao);
    }

    @NotTransactional
    public void testLoadMessagesStatistic() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 06, 01, 00, 00, 00);
        MessageStatistic messageStatistic = statisticDao.loadMessageStatistic(calendar.getTime());
        Assert.assertNotNull(messageStatistic);
        Assert.assertEquals(messageStatistic.getCompleted(), 2l);
        Assert.assertEquals(messageStatistic.getCancelled(), 1l);
        Assert.assertEquals(messageStatistic.getTotal(), 4l);
        Assert.assertEquals(messageStatistic.getActive(), 1l);
    }

    @NotTransactional
    public void testEmptyTables() throws Exception {
        TestUtil.clearDb("classpath:/statistic-dataset.xml", taskDataSource, applicationContext);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 06, 01, 00, 00, 00);
        MessageStatistic messageStatistic = statisticDao.loadMessageStatistic(calendar.getTime());
        Assert.assertNotNull(messageStatistic);
        Assert.assertEquals(messageStatistic.getCompleted(), 0);
        Assert.assertEquals(messageStatistic.getCancelled(), 0);
        Assert.assertEquals(messageStatistic.getTotal(), 0);
        Assert.assertEquals(messageStatistic.getActive(), 0);
    }

}
