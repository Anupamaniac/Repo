package com.rosettastone.succor.backgroundtask.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.service.HoptoadNotificationService;

/**
 * This exception handler is using for admins notification by email.
 *
 */
public class NotifyAdminExceptionHandler extends AbstractExceptionHandler {

	private static final Log LOG = LogFactory.getLog(NotifyAdminExceptionHandler.class);
	private EmailSender emailSender;
	private HoptoadNotificationService hoptoadNotificationService;

    @Override
    protected void doHandleException(Task task, Throwable exception) {
        try {
			emailSender.sendEmail(task, exception);
		}  catch (Exception e) {
			 if ((task != null) && (task.getMessage() != null))
			        hoptoadNotificationService.notifyingHopToad(task.getMessage().getMessage(), e);
			      else {
			    	  hoptoadNotificationService.notifyingHopToad(e);
			      }
            LOG.error("Exception caught", e);
        }
    }

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
