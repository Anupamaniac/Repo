package com.rosettastone.succor.backgroundtask.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.service.HoptoadNotificationService;

/**
 * This handler checks that task was run more than specified value times
 * and sends email to admin.
 */
public class RetryExceptionHandler extends AbstractExceptionHandler {

	private static final Log LOG = LogFactory.getLog(RetryExceptionHandler.class);
			
	public static final long ONE_MINUTE = 60 * 1000;

    private EmailSender emailSender;

    private int retryAmount;

    /* Minutes */
    private long retryInterval;
    
    private HoptoadNotificationService hoptoadNotificationService;

    @Override
    protected void doHandleException(Task task, Throwable exception)  {
        if (retryThresholdReached(task)) {
            try {
				emailSender.sendEmail(task, exception);
			}  catch (Exception e) {
				if ((task != null) && (task.getMessage() != null))
			          hoptoadNotificationService.notifyingHopToad(task.getMessage().getMessage(), e);
			        else {
				hoptoadNotificationService.notifyingHopToad(e);
			        }
	            LOG.error("Exception catched", e);
	        }
        }
    }

    @Override
    public long calculateDelay(Task task, Throwable exception) {
        if (retryThresholdReached(task)) {
            return super.calculateDelay(task, exception);
        }
        return retryInterval / retryAmount;
    }

    protected boolean retryThresholdReached(Task task) {
        return calculateRetryCount(task) >= retryAmount;
    }

    protected int calculateRetryCount(Task task) {
        //Do not count current execution
        return task.getHistory().size() - 1;
    }

    @Required
    public void setRetryAmount(int retryAmount) {
        this.retryAmount = retryAmount;
    }

    protected int getRetryAmount() {
        return retryAmount;
    }

    @Required
    public void setRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval * ONE_MINUTE;
    }

    @Required
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    public void setHoptoadNotificationService(HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}

	public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}
}
