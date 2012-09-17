package com.rosettastone.succor.timertask.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rosettastone.succor.TestUtil;
import com.rosettastone.succor.timertask.model.EmailReportHistory;

@Test(enabled = false)
@ContextConfiguration({ "classpath:/backgroundtask-context.xml", "classpath:/test-context.xml" })
@TransactionConfiguration(transactionManager = "taskTransactionManager")
public class EmailReportHistoryDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private DataSource taskDataSource;

    @Autowired
    private EmailReportHistoryDao emailReportHistoryDao;

    @BeforeMethod
    public void initDb() throws Exception {
        TestUtil.initDb("classpath:/email-report-dataset.xml", taskDataSource, applicationContext);
    }

    public void testCreate() {
        EmailReportHistory history = new EmailReportHistory();
        emailReportHistoryDao.create(history);
    }

    public void testLoadLast() {
        EmailReportHistory history = emailReportHistoryDao.loadLast();
        Assert.assertEquals(history.getId(), Long.valueOf(2));
    }

}
