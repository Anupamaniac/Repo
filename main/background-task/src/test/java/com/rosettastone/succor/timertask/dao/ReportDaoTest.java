package com.rosettastone.succor.timertask.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rosettastone.succor.TestUtil;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.timertask.model.StatisticBean;

@Test(enabled = false)
@ContextConfiguration({ "classpath:/backgroundtask-context.xml", "classpath:/test-context.xml" })
@TransactionConfiguration(transactionManager = "taskTransactionManager")
public class ReportDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    public static final long EMAIL_CUSTOMER = 3;
    public static final long SUPER_TIKET = 4;
    public static final long POSTAL_TICKET = 2;
    public static final long PHONE_TICKET = 6;
    public static final long INSTITUTIONAL_TICKET = 7;
    public static final long TOTAL_TICKET = 19;
    public static final Long REPORT_ENTITY_AFTER_CLEAN = 17L;

    @Autowired
    private DataSource taskDataSource;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private HibernateTemplate hibernateTemplate;
    
    @BeforeMethod
    public void initDb() throws Exception {
        TestUtil.initDb("classpath:/email-report-dataset.xml", taskDataSource, applicationContext);
    }

    public void testCreate() {
        reportDao.createNewEntity(ReportEntityType.EMAIL_CUSTOMER);
    }

    public void testGenerateStatistic() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate = format.parse("2010-08-31 23:59:59");
        Date endDate = format.parse("2010-10-01 00:00:00");
        StatisticBean statistic = reportDao.generateStatistic(startDate, endDate);
        Assert.assertEquals(1, statistic.get(ReportEntityType.MESSAGE_SKIPPED));
        Assert.assertEquals(2, statistic.get(ReportEntityType.EMAIL_ERROR));
        Assert.assertEquals(EMAIL_CUSTOMER, statistic.get(ReportEntityType.EMAIL_CUSTOMER));
        Assert.assertEquals(SUPER_TIKET, statistic.get(ReportEntityType.SUPER_TIKET));
        Assert.assertEquals(POSTAL_TICKET, statistic.get(ReportEntityType.POSTAL_TICKET));
        Assert.assertEquals(PHONE_TICKET, statistic.get(ReportEntityType.PHONE_TICKET));
        Assert.assertEquals(INSTITUTIONAL_TICKET, statistic.get(ReportEntityType.INSTITUTIONAL_TICKET));
        Assert.assertEquals(TOTAL_TICKET, statistic.getTicketTotal());
    }
    
    public void testCleanDb() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date endDate = format.parse("2010-09-06 00:00:08");
        reportDao.cleanDb(endDate);
        Long count = (Long) hibernateTemplate.find("select count(r) from ReportEntity r").get(0);
        Assert.assertEquals(count, REPORT_ENTITY_AFTER_CLEAN);
    }
}
