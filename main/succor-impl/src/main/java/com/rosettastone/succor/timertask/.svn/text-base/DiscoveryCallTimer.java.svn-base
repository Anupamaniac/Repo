package com.rosettastone.succor.timertask;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.dao.DiscoveryCallDao;
import com.rosettastone.succor.dao.DiscoveryCallEntity;
import com.rosettastone.succor.service.HoptoadNotificationService;
import com.rosettastone.succor.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

/**
 *
 * Task for scheduler.
 * This task is main point for Discovery Call trigger
 * Every day at 12:00 we get information from Oracle database and create tasks in MySQL DB.
 */
public class DiscoveryCallTimer {

    private static final Log LOG = LogFactory.getLog(DiscoveryCallTimer.class);

    public static final int LAST_N_DAYS = 10;

    private DiscoveryCallDao discoveryCallDao;

    private TaskManager taskManager;
    private HoptoadNotificationService hoptoadNotificationService;

    /**
     * The method is called by timer thread
     */
    public void timer() {
        LOG.info("Start discovery call timer task.");
        Task t = null;
        Date now = new Date();
        // load entities that was created less that 10 days ago.
        List<DiscoveryCallEntity> entities = discoveryCallDao.loadForDate(DateUtils.getDate(now, LAST_N_DAYS));
        int count = entities.size();
        int i = 0;
        for (DiscoveryCallEntity entity : entities) {
            try {
                Task task = new Task();
                t = task;
                Message message = serializeEntity(entity);
                task.setMessage(message);
                task.setBeanName("discoveryCallService");
                task.setMethodName("processEntity");
                task.setRawArguments(new Object[]{entity, now});
                task.setNextRun(calculateStartDate(now, i, count));
                taskManager.createDiscoveryCallTask(task);
            } catch (Exception e) {
            	if ((t != null) && (t.getMessage() != null))
                    hoptoadNotificationService.notifyingHopToad(t.getMessage().getMessage(), e);
                  else {
    			hoptoadNotificationService.notifyingHopToad(e);
                  }
                LOG.error("Can not create task for discovery call entity.", e);
            } finally {
                i++;
            }
        }
        LOG.debug("End discovery call timer task.");
    }

    private Date calculateStartDate(Date now, int index, int count) {
        Date endDate = org.apache.commons.lang.time.DateUtils.addHours(now, 12);
        long delta = (endDate.getTime() - now.getTime()) / count;
        return new Date(now.getTime() + delta * index);
    }

    /**
     * Serialize discovery call DB entity into JSON message.
     * @param entity
     * @return JSON message
     * @throws IOException
     */
    private Message serializeEntity(DiscoveryCallEntity entity) throws IOException {
        Message message = new Message();
        StringWriter writer = new StringWriter();
        new ObjectMapper().writeValue(writer, entity);
        message.setMessage(writer.toString());
        return message;
    }

    @Required
    public void setDiscoveryCallDao(DiscoveryCallDao discoveryCallDao) {
        this.discoveryCallDao = discoveryCallDao;
    }

    @Required
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}

	public void setHoptoadNotificationService(
			HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}
}
