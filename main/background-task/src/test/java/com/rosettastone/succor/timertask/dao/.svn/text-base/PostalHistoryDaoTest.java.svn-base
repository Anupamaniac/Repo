package com.rosettastone.succor.timertask.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.timertask.model.PostalHistory;
import com.rosettastone.succor.timertask.model.PostalHistoryStatus;

@Test(enabled = false)
@ContextConfiguration({ "classpath:/backgroundtask-context.xml", "classpath:/test-context.xml" })
@TransactionConfiguration(transactionManager = "taskTransactionManager")
public class PostalHistoryDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final int UNPROCESSED_EN_LIST_SIZE = 3;
    private static final int UNPROCESSED_JA_LIST_SIZE = 2;

    private static final int YEAR = 2010;
    private static final int MONTH = 9;
    private static final int DAY = 9;

    @Autowired
    private PostalHistoryDao postalHistoryDao;

    @Autowired
    private DataSource taskDataSource;

    @BeforeMethod
    public void initDb() throws DatabaseUnitException, SQLException, IOException {
        DatabaseDataSourceConnection connection = new DatabaseDataSourceConnection(taskDataSource);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        FlatXmlDataSet xmlDdataset = builder.build((applicationContext
                .getResource("classpath:/postalhistory-dataset.xml").getInputStream()));
        ReplacementDataSet dataSet = new ReplacementDataSet(xmlDdataset);
        dataSet.addReplacementObject("[NULL]", null);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "`?`");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        connection.close();
    }

    public void testConfiguration() {
        Assert.assertNotNull(postalHistoryDao);
    }

    public void testLoadByPeriodEn() {
        List<PostalHistory> list = postalHistoryDao.loadUnprocessed(Locale.ENGLISH);
        Assert.assertEquals(list.size(), UNPROCESSED_EN_LIST_SIZE);
    }

    public void testLoadByPeriodJa() {
        List<PostalHistory> list = postalHistoryDao.loadUnprocessed(Locale.JAPANESE);
        Assert.assertEquals(list.size(), UNPROCESSED_JA_LIST_SIZE);
    }

    public void testCreate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(YEAR, MONTH, DAY, 0, 0, 0);

        PostalHistory history = new PostalHistory();
        history.setCreatedAt(calendar.getTime());
        history.setContactReason(ContactReasonType.LOG_BACK_IN_ENC_30DAYS);
        history.setCustomerName("New customer");
        history.setLanguage(TicketLanguageType.AMERICAN_ENGLISH);
        history.setProductLevel("L4");
        history.setStatus(PostalHistoryStatus.UNPROCESSED);
        history.setShippingAddress("customer shipping address");
        history.setSupportLocale("en");
        postalHistoryDao.create(history);
        List<PostalHistory> list = postalHistoryDao.loadUnprocessed(Locale.ENGLISH);
        Assert.assertEquals(list.size(), UNPROCESSED_EN_LIST_SIZE + 1);
    }

    public void testUpdateRemove() {
        List<PostalHistory> list = postalHistoryDao.loadUnprocessed(Locale.ENGLISH);
        for (PostalHistory history : list) {
            history.setStatus(PostalHistoryStatus.PROCESSED);
        }
        postalHistoryDao.update(list);
        list = postalHistoryDao.loadUnprocessed(Locale.ENGLISH);
        for (PostalHistory history : list) {
            Assert.assertEquals(history.getStatus(), PostalHistoryStatus.PROCESSED);
        }
        int removed = postalHistoryDao.removeProcessedOlderThan(new Date());
        Assert.assertEquals(removed, UNPROCESSED_EN_LIST_SIZE);

        list = postalHistoryDao.loadUnprocessed(Locale.JAPANESE);
        Assert.assertEquals(list.size(), UNPROCESSED_JA_LIST_SIZE);
    }

}
