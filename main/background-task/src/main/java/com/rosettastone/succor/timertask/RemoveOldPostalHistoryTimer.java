package com.rosettastone.succor.timertask;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.timertask.dao.PostalHistoryDao;

public class RemoveOldPostalHistoryTimer {

    private static final Log LOG = LogFactory.getLog(RemoveOldPostalHistoryTimer.class);

    private static final long MONTH = 30 * 24 * 60 * 60 * 1000L;

    private PostalHistoryDao postalHistoryDao;

    public void timer() {
        LOG.info("Remove old PostalHistory form DB");
        postalHistoryDao.removeProcessedOlderThan(new Date(System.currentTimeMillis() - MONTH));
        LOG.debug("Remove old PostalHistory form DB end");
    }

    @Required
    public void setPostalHistoryDao(PostalHistoryDao postalHistoryDao) {
        this.postalHistoryDao = postalHistoryDao;
    }

}
