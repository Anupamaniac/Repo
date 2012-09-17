package com.rosettastone.succor.backgroundtask.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.service.HoptoadNotificationService;

public class CancelTaskExceptionHandler extends AbstractExceptionHandler {

	private static final Log LOG = LogFactory.getLog(CancelTaskExceptionHandler.class);
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
            LOG.error("Exception catched", e);
        }
    }

    @Override
    protected long calculateDelay(Task task, Throwable exception) {
        return NO_PAUSE_DELAY;
    }

    @Override
    public void updateTask(Task task, Throwable exception) {
        task.setStatus(TaskStatus.CANCELLED);
        task.setNextRun(null);
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
