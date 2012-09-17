package com.rosettastone.succor.timertask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.mail.MessagingException;

import com.rosettastone.succor.service.FtpService;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.timertask.dao.PostalHistoryDao;
import com.rosettastone.succor.timertask.model.PostalHistory;
import com.rosettastone.succor.timertask.model.PostalHistoryStatus;

@Test(enabled = true)
@ContextConfiguration(locations = {"classpath:/test-context.xml"})
public class SendPostalBatchTimerTest  extends AbstractTestNGSpringContextTests {
    private static final long DAY = 24 * 60 * 60 * 1000L;
    private static final int CREATE_OBJECT_AMOUNT = 3;

    private PostalHistoryDao postalHistoryDao;

    private SendPostalBatchTimer timer;

    @Autowired
    private FtpService ftpService;

    @BeforeClass
    public void init() {
        postalHistoryDao = EasyMock.createMock(PostalHistoryDao.class);
        timer = new SendPostalBatchTimer();
        timer.setPostalHistoryDao(postalHistoryDao);
        timer.setReadWriteLock(new ReentrantReadWriteLock(true));
        List<Locale> supportedLocales = new ArrayList<Locale>();
        supportedLocales.add(Locale.ENGLISH);
        supportedLocales.add(Locale.JAPANESE);
        timer.setSupportedLocales(supportedLocales);

    }

    @BeforeMethod
    public void resetMockObjects() {
        EasyMock.reset(postalHistoryDao);
    }

    @Test(enabled = true)
    public void testJob() throws MessagingException, IOException {
        timer.setFtpService(ftpService);
        List<PostalHistory> list = createTestObjects();
        postalHistoryDao.loadUnprocessed(Locale.ENGLISH);
        EasyMock.expectLastCall().andReturn(list);
        postalHistoryDao.update(list);
        postalHistoryDao.loadUnprocessed(Locale.JAPANESE);
        EasyMock.expectLastCall().andReturn(list);
        postalHistoryDao.update(list);
        EasyMock.replay(postalHistoryDao);

        timer.mainTimer();

        EasyMock.verify(postalHistoryDao);
    }

    public List<PostalHistory> createTestObjects() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime() - DAY);
        Date createdAt = calendar.getTime();

        List<PostalHistory> list = new ArrayList<PostalHistory>();
        for (int i = 0; i < CREATE_OBJECT_AMOUNT; i++) {
            PostalHistory history = new PostalHistory();
            history.setCreatedAt(createdAt);
            history.setContactReason(ContactReasonType.LOG_BACK_IN_ENC_30DAYS);
            history.setCustomerName("New customer");
            history.setLanguage(TicketLanguageType.AMERICAN_ENGLISH);
            history.setProductLevel("L4");
            history.setStatus(PostalHistoryStatus.UNPROCESSED);
            history.setShippingAddress("customer shipping address");
            list.add(history);
        }
        return list;
    }

}
