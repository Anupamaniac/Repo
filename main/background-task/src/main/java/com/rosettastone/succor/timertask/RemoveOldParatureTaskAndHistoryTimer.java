package com.rosettastone.succor.timertask;


import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.impl.TaskDao;
import com.rosettastone.succor.service.HoptoadNotificationService;

public class RemoveOldParatureTaskAndHistoryTimer {
	private static final Log LOG = LogFactory.getLog(RemoveOldPostalHistoryTimer.class);
	 private HoptoadNotificationService hoptoadNotificationService;

    private TaskDao taskDao;

	public TaskDao getTaskDao() {
		return taskDao;
	}

	@Required
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	private void cleanDb() {
		LOG.debug("Inside cleanDB method");
          try {

              taskDao.deleteOldTasks(DateUtils.addMonths(new Date(), -1));
        	  //taskDao.deleteOldTasks(DateUtils.addDays(new Date(), -10));
          } catch (Exception e) {
        	  if(hoptoadNotificationService!=null)
        	  hoptoadNotificationService.notifyingHopToad(e);
              LOG.error("Can not delete old tasks from DB", e);
          }
      }
	public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}

	public void setHoptoadNotificationService(
			HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}
    
}
