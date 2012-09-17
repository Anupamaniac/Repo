package com.rosettastone.succor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.rosettastone.succor.backgroundtask.impl.ParatureTaskTrackerDao;
import com.rosettastone.succor.backgroundtask.model.ParatureAction;
import com.rosettastone.succor.backgroundtask.model.ParatureActionStatus;
import com.rosettastone.succor.backgroundtask.model.ParatureTaskHistory;
import com.rosettastone.succor.backgroundtask.model.ParatureTaskTracker;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.model.parature.Action;
import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.model.parature.Ticket;
import com.rosettastone.succor.parature.ParatureHttpClient;
import com.rosettastone.succor.service.HoptoadNotificationService;

/**
 * The {@code ParatureHttpDelegator} persists the information before sending a
 * Parature PUT and POST request and after receiving response. Checks for status
 * of sub-request of single task and only resends the ACTIVE sub-requests.
 * Sub-requests with completed status will not be sent in consecutive attempt.
 * This will avoid dual updates even on reschedule of task.
 */

public class ParatureHttpDelegator {

    private ParatureTaskTrackerDao paratureTaskTrackerDao;

    private TicketSerializer ticketSerializer;

    private ActionSerializer actionSerializer;

    private ParatureHttpClient paratureHttpClient;

    private ThreadLocal<Task> currentTask;
    private HoptoadNotificationService hoptoadNotificationService;

    private static final Log LOG = LogFactory.getLog(ParatureHttpDelegator.class);

    /**
     * captures and persist useful information in Parature_task_tracker and
     * parature_task_history before and after sending the parature POST request.
     * 
     * @param url
     * @param is
     * @param paratureAction
     */
    public String sendPostRequest(String url, InputStream is,Object ticketOrAction, ParatureAction paratureAction) {
        ParatureTaskTracker paratureTaskTracker;
        ParatureTaskHistory paratureTaskHistory = null;// **
        String response = null;// **
        String ticketId = null;
        Task task = currentTask.get();
        paratureTaskTracker = findParatureTaskTracker(task, paratureAction, ticketId);
        if (paratureTaskTracker.getStatus().equals(ParatureActionStatus.ACTIVE)) {
            paratureTaskHistory = createNewHistory(paratureTaskTracker, ticketOrAction);// **
            updateRetryCount(paratureTaskTracker);
            try {
                response = paratureHttpClient.sendPostRequest(url, is);
            } catch (ApplicationException e) {
                updateParatureTaskTracker(task, paratureTaskTracker, e, paratureTaskHistory);
                throw e;
            }

            updateParatureTaskTracker(task, paratureTaskTracker, response, paratureTaskHistory);
        }
        return response;
    }

    /**
     * captures and persist useful information in Parature_task_tracker and
     * parature_task_history before and after sending the parature PUT request.
     * 
     * @param url
     * @param is
     * @param paratureAction
     */

    public String sendPutRequest(String url, InputStream is, String ticketId, Object ticketOrAction,
            ParatureAction paratureAction) {
        ParatureTaskTracker paratureTaskTracker;
        ParatureTaskHistory paratureTaskHistory = null;// **
        String response = null;// **
        Task task = currentTask.get();
        paratureTaskTracker = findParatureTaskTracker(task, paratureAction, ticketId);
        if (paratureTaskTracker.getStatus().equals(ParatureActionStatus.ACTIVE)) {
            paratureTaskHistory = createNewHistory(paratureTaskTracker, ticketOrAction);// **
            updateRetryCount(paratureTaskTracker);
            try {

                response = paratureHttpClient.sendPutRequest(url, is);
            } catch (ApplicationException e) {
                updateParatureTaskTracker(task, paratureTaskTracker, e, paratureTaskHistory);
                throw e;
            }
            updateParatureTaskTracker(task, paratureTaskTracker, response, paratureTaskHistory);
        }
        return response;
    }

    public String getCurrentTrackerId(ParatureAction paratureAction) {
        Task task = currentTask.get();
        ParatureTaskTracker paratureTaskTracker = findParatureTaskTracker(task, paratureAction, null);
        return paratureTaskTracker.getTicketId();
    }

    /**
     * finds the task tracker if already persisted else creates a new entry in
     * parature_task_tracker
     * 
     * @param task
     * @param paratureAction
     * @param ticketId
     */
    public ParatureTaskTracker findParatureTaskTracker(Task task, ParatureAction paratureAction, String ticketId) {
        Hibernate.initialize(task.getParatureTaskTracker());
        Set<ParatureTaskTracker> paratureTasks = task.getParatureTaskTracker();
        ParatureTaskTracker paratureTaskTracker = null;
        for (ParatureTaskTracker ptt : paratureTasks) {
            if (ptt.getTask().equals(task) && paratureAction.equals(ptt.getParatureAction())) {
                paratureTaskTracker = ptt;
                break;
            }
        }
        if (paratureTaskTracker == null) {
            paratureTaskTracker = createParatureTaskTracker(task, paratureAction, ticketId);
            paratureTaskTrackerDao.create(paratureTaskTracker);
        }
        return paratureTaskTracker;
    }

    /**
     * creates a new entry in parature_task_tracker
     * 
     * @param task
     * @param paratureAction
     * @param ticketId
     */
    public ParatureTaskTracker createParatureTaskTracker(Task task, ParatureAction paratureAction, String ticketId) {
        ParatureTaskTracker paratureTaskTracker = new ParatureTaskTracker();
        paratureTaskTracker.setParatureAction(paratureAction);
        paratureTaskTracker.setStatus(ParatureActionStatus.ACTIVE);
        paratureTaskTracker.setTicketId(ticketId);
        paratureTaskTracker.setTask(task);
        task.addParatureTaskTracker(paratureTaskTracker);
        return paratureTaskTracker;
    }

    /**
     * updates status of a sub-task after sending request, after successful
     * completion of sub-task and response is persisted.
     * 
     * @param task
     * @param paratureTaskTracker
     * @param exception
     * @param paratureTaskHistory
     */
    public void updateParatureTaskTracker(Task task, ParatureTaskTracker paratureTaskTracker, String response,
            ParatureTaskHistory paratureTaskHistory) {
        paratureTaskTracker.setStatus(ParatureActionStatus.COMPLETED);
        paratureTaskHistory.setResponse(response);
        paratureTaskHistory.setResponseRcvdAt(new Date());
        paratureTaskTrackerDao.update(paratureTaskTracker);
    }

    /**
     * updates status of a sub-task after sending request, when some exception
     * occurred at parature end
     * 
     * @param task
     * @param paratureTaskTracker
     * @param exception
     * @param paratureTaskHistory
     */
    public void updateParatureTaskTracker(Task task, ParatureTaskTracker paratureTaskTracker, ApplicationException e,
            ParatureTaskHistory paratureTaskHistory) {
        paratureTaskTracker.setStatus(ParatureActionStatus.ACTIVE);
        paratureTaskHistory.setParatureException(e.getMessage());
        paratureTaskHistory.setResponseRcvdAt(new Date());
        paratureTaskTrackerDao.update(paratureTaskTracker);
    }

    /**
     * captures number of attempts made per sub-task
     * 
     * @param url
     * @param is
     * @param paratureAction
     */
    public void updateRetryCount(ParatureTaskTracker paratureTaskTracker) {
        paratureTaskTracker.setRetryCount(paratureTaskTracker.getRetryCount() + 1);
        paratureTaskTrackerDao.update(paratureTaskTracker);
    }

    /**
     * creates an entry in parature_task_history table. Every attempt of
     * execution of sub-task creates an entity in history
     * 
     * @param paratureTaskTracker
     * @param ticketo
     */
    public ParatureTaskHistory createNewHistory(ParatureTaskTracker paratureTaskTracker, Object ticketo) {
        ParatureTaskHistory paratureTaskHistory = new ParatureTaskHistory();
        InputStream is = null;
        if (ticketo instanceof Ticket) {
            Ticket ticket = (Ticket) ticketo;
            is = ticketSerializer.serialize(ticket);
        } else if (ticketo instanceof Action) {
            Action action = (Action) ticketo;
            is = actionSerializer.serialize(action);
        }
        String str = "";
        try {
            str = convertStreamToString(is);
            LOG.debug(str);
        } catch (IOException e) {
        	if(hoptoadNotificationService!=null)
        	hoptoadNotificationService.notifyingHopToad(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        paratureTaskHistory.setRequest(str);
        paratureTaskHistory.setRequestSentAt(new Date());
        paratureTaskHistory.setParatureTaskTracker(paratureTaskTracker);
        paratureTaskTracker.getParatureTaskHistory().add(paratureTaskHistory);
        paratureTaskTrackerDao.update(paratureTaskTracker);
        return paratureTaskHistory;
    }

    public String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the Reader.read(char[]
         * buffer) method. We iterate until the Reader return -1 which means
         * there's no more data to read. We use the StringWriter class to
         * produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public ParatureTaskTrackerDao getParatureTaskTrackerDao() {
        return paratureTaskTrackerDao;
    }

    public void setParatureTaskTrackerDao(ParatureTaskTrackerDao paratureTaskTrackerDao) {
        this.paratureTaskTrackerDao = paratureTaskTrackerDao;
    }

    public TicketSerializer getTicketSerializer() {
        return ticketSerializer;
    }

    public void setTicketSerializer(TicketSerializer ticketSerializer) {
        this.ticketSerializer = ticketSerializer;
    }

    public ParatureHttpClient getParatureHttpClient() {
        return paratureHttpClient;
    }

    public void setParatureHttpClient(ParatureHttpClient paratureHttpClient) {
        this.paratureHttpClient = paratureHttpClient;
    }

    public ThreadLocal<Task> getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(ThreadLocal<Task> currentTask) {
        this.currentTask = currentTask;
    }

    public ActionSerializer getActionSerializer() {
        return actionSerializer;
    }

    public void setActionSerializer(ActionSerializer actionSerializer) {
        this.actionSerializer = actionSerializer;
    }
    public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}

	public void setHoptoadNotificationService(
			HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}
}
