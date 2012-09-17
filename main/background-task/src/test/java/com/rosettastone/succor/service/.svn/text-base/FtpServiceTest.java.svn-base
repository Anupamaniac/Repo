package com.rosettastone.succor.service;

/**
 * User: mixim
 * Date: 5/30/11
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.rosettastone.succor.exception.FTPException;
import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.timertask.CSVConverter;
import com.rosettastone.succor.timertask.model.PostalHistory;
import com.rosettastone.succor.timertask.model.PostalHistoryStatus;

@Test
@ContextConfiguration(locations = { "classpath:/test-context.xml" })
public class FtpServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    FtpService ftpService;

    private static final Log LOG = LogFactory.getLog(FtpServiceTest.class);


    public static List<PostalHistory> getFakeHistoryListKo() {

        List<PostalHistory> list = new ArrayList<PostalHistory>();

        PostalHistory postalHistory = new PostalHistory();
        postalHistory.setContactReason(ContactReasonType.LEVEL_1_STUDIO_COMPLETION);
        postalHistory.setCreatedAt(new Date());
        postalHistory.setCustomerName("ロゼッタストーン");
        postalHistory.setEmail("email");
        postalHistory.setGuid("11111111111111111");
        postalHistory.setId(1L);
        postalHistory.setLanguage(TicketLanguageType.RUSSIAN);
        postalHistory.setProcessedAt(new Date());
        postalHistory.setProductLevel("Product Level");
        postalHistory.setStatus(PostalHistoryStatus.UNPROCESSED);
        postalHistory.setShippingAddress("Ship addr");
        postalHistory.setSupportLocale(Locale.US.toString());

        return list;
    }

    public static List<PostalHistory> getFakeHistoryList() {

        List<PostalHistory> list = new ArrayList<PostalHistory>();

        PostalHistory postalHistory = new PostalHistory();
        postalHistory.setContactReason(ContactReasonType.LEVEL_1_STUDIO_COMPLETION);
        postalHistory.setCreatedAt(new Date());
        postalHistory.setCustomerName("Cus Name1");
        postalHistory.setEmail("email");
        postalHistory.setGuid("11111111111111111");
        postalHistory.setId(1L);
        postalHistory.setLanguage(TicketLanguageType.RUSSIAN);
        postalHistory.setProcessedAt(new Date());
        postalHistory.setProductLevel("Product Level");
        postalHistory.setStatus(PostalHistoryStatus.UNPROCESSED);
        postalHistory.setShippingAddress("Ship addr");
        postalHistory.setSupportLocale(Locale.US.toString());

        list.add(postalHistory);

        postalHistory = new PostalHistory();
        postalHistory.setContactReason(ContactReasonType.LEVEL_1_STUDIO_COMPLETION);
        postalHistory.setCreatedAt(new Date());
        postalHistory.setCustomerName("Cus Name2");
        postalHistory.setEmail("email");
        postalHistory.setGuid("11111111111111111");
        postalHistory.setId(2L);
        postalHistory.setLanguage(TicketLanguageType.RUSSIAN);
        postalHistory.setProcessedAt(new Date());
        postalHistory.setProductLevel("Product Level2");
        postalHistory.setStatus(PostalHistoryStatus.UNPROCESSED);
        postalHistory.setShippingAddress("Ship addr2");
        postalHistory.setSupportLocale(Locale.JAPAN.toString());

        list.add(postalHistory);

        postalHistory = new PostalHistory();
        postalHistory.setContactReason(ContactReasonType.LEVEL_1_STUDIO_COMPLETION);
        postalHistory.setCreatedAt(new Date());
        postalHistory.setCustomerName("Cus Name3");
        postalHistory.setEmail("email");
        postalHistory.setGuid("11111111111111111");
        postalHistory.setId(3L);
        postalHistory.setLanguage(TicketLanguageType.RUSSIAN);
        postalHistory.setProcessedAt(new Date());
        postalHistory.setProductLevel("Product Level2");
        postalHistory.setStatus(PostalHistoryStatus.UNPROCESSED);
        postalHistory.setShippingAddress("Ship addr2");
        postalHistory.setSupportLocale(Locale.KOREA.toString());

        list.add(postalHistory);

        return list;
    }

    @Test(enabled = true)
    void testConfiguration() {
        Assert.assertNotNull(ftpService);
        Assert.assertNotNull(ftpService.getFtpServerName());
        Assert.assertNotNull(ftpService.getFtpDir());
        Assert.assertNotNull(ftpService.getUserName());
        Assert.assertNotNull(ftpService.getPassword());
    }

    @Test(enabled = false)
    public void testSaveFile() {

        List<PostalHistory> list = getFakeHistoryList();
        // LOG.debug("There are " + list.size() + " unprocessed entities");
        if (list.size() == 0) {
            return;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // LOG.debug("processForLocale() postal information");
        try {
            CSVConverter.convertWithReturn(list, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ftpService.saveToFile(outputStream.toByteArray(), Locale.US);
        } catch (FTPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        byte[] ret = getWithFTPClient(ftpService, Locale.US);
        Boolean success = Boolean.FALSE;
        if (isEquals(ret, outputStream.toByteArray()))
            success = Boolean.TRUE;
        else
            LOG.debug("TEST FAILED");
        Assert.assertEquals(Boolean.TRUE, success);
    }

    @Test(enabled = true)
    public void testSaveFileKo() {

        List<PostalHistory> list = getFakeHistoryListKo();
        // LOG.debug("There are " + list.size() + " unprocessed entities");
        if (list.size() == 0) {
            return;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // LOG.debug("processForLocale() postal information");
        try {
            CSVConverter.convertWithReturn(list, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ftpService.saveToFile(outputStream.toByteArray(), Locale.US);
        } catch (FTPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        byte[] ret = getWithFTPClient(ftpService, Locale.US);
        Boolean success = Boolean.FALSE;
        if (isEquals(ret, outputStream.toByteArray()))
            success = Boolean.TRUE;
        else
            LOG.debug("TEST FAILED");
        Assert.assertEquals(Boolean.TRUE, success);
    }

    byte[] getWithFTPClient(FtpService ftpService, Locale locale) {

        Date saveDate = new Date();
        String fileName = ftpService.getLastSavedFileName();
        LOG.debug("FTP FileName for retrieve '" + fileName + "'");

        String hostName = ftpService.getFtpServerName();
        String userName = ftpService.getUserName();
        String password = ftpService.getPassword();
        FTPClient ftp = null;

        InputStream in = null;
        try {
            ftp = new FTPClient();
            ftp.connect(hostName);
            ftp.login(userName, password);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.changeWorkingDirectory(ftpService.getFtpDir());

            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                LOG.error("\n\tFTP Connection FAIL");
            }
            in = ftp.retrieveFileStream(fileName);
            int i = 0;
            byte[] b = null;
            while ((i = in.available()) > 0) {
                b = new byte[i];
                in.read(b);
            }
            LOG.debug("FTP SUCCESS get file");
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    Boolean isEquals(byte[] b1, byte[] b2) {
        Boolean result = Boolean.TRUE;
        if (b1.length != b2.length)
            return Boolean.FALSE;
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                result = Boolean.FALSE;
                break;
            }
        }
        return result;
    }

}