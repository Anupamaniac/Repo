package com.rosettastone.succor.timertask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import com.rosettastone.succor.exception.FTPException;
import com.rosettastone.succor.service.FtpService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.timertask.dao.PostalHistoryDao;
import com.rosettastone.succor.timertask.model.PostalHistory;
import com.rosettastone.succor.timertask.model.PostalHistoryStatus;

/**
 * Creates postal report and saves it on the ftp server.
 * Processes postal history records with "UNPROCESSED" status and
 * sets the status "PROCESSED" after processing
 * @see     com.rosettastone.succor.service.FtpService
 * @see     com.rosettastone.succor.timertask.CSVConverter
 */

public class SendPostalBatchTimer {
    private static final int DEFAULT_MAX_REPEAT_COUNT = 200;

    private static final Log LOG = LogFactory.getLog(SendPostalBatchTimer.class);
   
    private PostalHistoryDao postalHistoryDao;
    private ReadWriteLock readWriteLock;    
    private List<Locale> supportedLocales; 
    
    private int maxRepeatCount = DEFAULT_MAX_REPEAT_COUNT;
    private int repeatCount;
    private boolean lastExecuteSuccessed = true;

    private FtpService ftpService;

    /**
     * Main method for start processing of postal history.
     * Calls {@link #execute()} method
     * @throws IOException
     * @throws FTPException
     */
    public synchronized void mainTimer() throws IOException, FTPException {
        LOG.debug("mainTimer");
        lastExecuteSuccessed = false;
        execute();
        lastExecuteSuccessed = true;
    }

    /**
     * Helper method for start processing of postal history.
     * Calls {@link #execute()} method if mainTimer worked with failure
     * @throws IOException
     * @throws FTPException
     */
    public synchronized void checkFailureTimer() throws IOException, FTPException {
        LOG.debug("checkFailureTimer");
        if (lastExecuteSuccessed || repeatCount > maxRepeatCount) {
            return;
        }
        execute();
        lastExecuteSuccessed = true;
    }

    /**
     * Calls {@link #processForLocale(java.util.Locale)} method for available locales
     * @throws IOException
     * @throws FTPException
     */
    private void execute() throws IOException, FTPException {
        LOG.debug("Load list of postal information");
        repeatCount++;
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            for (Locale locale : supportedLocales) {
                processForLocale(locale);
            }
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Processes the records for locale.
     * Return if no data for processing or convertWithReturn method return <tt>false</tt>
     * sets the status "PROCESSED" for processed records
     * @throws IOException
     * @throws FTPException
     */
    private void processForLocale(Locale locale) throws IOException, FTPException {
        List<PostalHistory> list = postalHistoryDao.loadUnprocessed(locale);
        LOG.debug("There are '" + (list!=null?list.size():"null") + "' unprocessed entities for locale '" + locale + "'");
        if (list == null || list.size() == 0) {
            return;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if( !CSVConverter.convertWithReturn(list, outputStream) ){
            return;
        }
        if( ftpService.saveToFile(outputStream.toByteArray(), locale) ){
            Date now = new Date();
            for (PostalHistory history : list) {
                history.setStatus(PostalHistoryStatus.PROCESSED);
                history.setProcessedAt(now);
            }
            postalHistoryDao.update(list);
        }
        outputStream.close();
    }

    @Required
    public void setPostalHistoryDao(PostalHistoryDao postalHistoryDao) {
        this.postalHistoryDao = postalHistoryDao;
    }

    @Required
    public void setReadWriteLock(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Required
    public void setSupportedLocales(List<Locale> supportedLocales) {
        this.supportedLocales = supportedLocales;
    }

    @Required
    public void setFtpService(FtpService ftpService) {
        this.ftpService = ftpService;
    }

}
