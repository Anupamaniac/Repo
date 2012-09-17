package com.rosettastone.succor.rules;

import java.io.IOException;
import java.util.Properties;

import org.easymock.EasyMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.Message;
import com.rosettastone.succor.timertask.dao.ReportDao;

@Test
public class RulesInvokerTest {

    private RulesInvoker rulesInvoker;

    @BeforeClass
    public void init() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/application.properties"));
        rulesInvoker = new RulesInvoker();
        ReportDao reportDao = EasyMock.createMock(ReportDao.class);
        rulesInvoker.setReportDao(reportDao);
    }

    @AfterClass
    public void destroy() {
        rulesInvoker.destroy();
    }

    public void smokeTest() {
        Event event = new Event("{}");
        event.setMessage(new Message());
        event.getMessage().setGuid("1");
        rulesInvoker.invokeForEvent(event);
    }
}
