package com.rosettastone.succor.service.sms;

import com.rosettastone.succor.exception.SMSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * User: Nikolay Sazonov
 * Date: 5/4/11
 */
@Test
@ContextConfiguration(locations = {"classpath:sms-context.xml"})
public class SMSSenderTest extends AbstractTestNGSpringContextTests {

    private static final Log LOG = LogFactory.getLog(SMSSenderTest.class);

    @Autowired
    private SMSSender smsSender;

    @Autowired
    private ClickatellPlatform clickatellService;

    @Autowired
    private CellTrustPlatform cellTrustService;

    @Test(enabled = false)
    public void testClickatell() throws SMSException {
        String id = clickatellService.sendMessage("79231816463", "privet");
        LOG.info("messageId:" + id);
    }

    @Test(enabled = false)
    public void testCellTrust() throws SMSException {
       cellTrustService.sendMessage("79231816463", "privet");
    }
}
