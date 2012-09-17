package com.rosettastone.succor.mdp;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.rosettastone.succor.bandit.EventParser;
import com.rosettastone.succor.service.HoptoadNotificationService;
import com.rosettastone.succor.utils.TaskPriorityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.impl.ExceptionNotificationStatusWatcher;
import com.rosettastone.succor.backgroundtask.impl.NotifyAdminExceptionHandler;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskType;

/**
 * The {@code RabbitMqListener} reads messages from rabbitmq queue and initiates they processing.
 */

public class RabbitMqListener extends Thread implements BeanNameAware {

    public static final long ONE_SECOND = 1000;

    private static final String EXCANGE_TYPE = "fanout";

    private final Log log = LogFactory.getLog(RabbitMqListener.class);

    private String threadName;
    private RabbitMqChannelManager channelManager;
    private TaskManager taskManager;
    private PlatformTransactionManager transactionManager;
    private ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher;
    private NotifyAdminExceptionHandler notifyAdminExceptionHandler;

    private long noMessageDelay;
    private long errorDelay;

    private String excange;
    private String queue;

    private volatile boolean runnig;
    private boolean declared;
    private TransactionStatus transactionStatus;
    private EventParser eventParser;

    /**
     * Fake task. Need only for error notifier. 
     */
    private Task task;

    private AtomicInteger messageCount = new AtomicInteger();

    private TaskPriorityService taskPriorityService;
    
    private HoptoadNotificationService hoptoadNotificationService;

    public int getMessageCount() {
        return messageCount.get();
    }

    /**
     * Makes some actions after object was created.
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        task = new Task();
        task.setType(TaskType.RABBIT_MQ);

        runnig = true;
        this.setName(threadName);
        this.setDaemon(true);
        this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
            	if(hoptoadNotificationService!=null)
            	hoptoadNotificationService.notifyingHopToad(e);
                e.printStackTrace();
            }
        });
        this.start();
    }

    /**
     * Makes some action before object will destroyed.
     */
    @PreDestroy
    public void shutdown() {
        runnig = false;
        this.interrupt();
    }

    /**
     * Called after start.
     * Calls {@code reciveAndAsk} method.
     */
    @Override
    public void run() {
        log.debug("Main loop started");
        while (runnig) {
            reciveAndAsk();
            log.info("RMQ alive");
        }
        log.debug("Main loop stopped");
    }

    /**
     * Reads message from queue and calls {@code processMessage} method for processing of message.
     */
    private void reciveAndAsk() {
        boolean needToSleep = false;
        try {
            if (!declared) {
                declare();
            }
            beginTransactions();
            boolean noAck = false;
            Channel channel = channelManager.currentChannel();
            GetResponse getResponse = channel.basicGet(queue, noAck);
            if (getResponse != null && getResponse.getBody() != null) {
                String message = new String(getResponse.getBody());
                processMessage(message);
                sendAck(getResponse.getEnvelope().getDeliveryTag());
                messageCount.set(getResponse.getMessageCount());
            } else {
                needToSleep = true;
            }
            commitTransactions();
            exceptionNotificationStatusWatcher.cleanNotificationStatuses(task);
        } catch (Throwable t) {
        	if(hoptoadNotificationService!=null)
        	hoptoadNotificationService.notifyingHopToad(t);
            log.error("Can not process message", t);
            rollbackTransactions();
            notifyAdminExceptionHandler.handleException(task, t);
            pause(errorDelay);
        } finally {
            channelManager.closeCurrentChannel();
        }
        if (needToSleep) {
            pause(noMessageDelay);
        }
    }

    private void declare() throws IOException {
        Channel channel = channelManager.currentChannel();
        boolean durable = true;
        channel.exchangeDeclare(excange, EXCANGE_TYPE, durable);
        channel.queueDeclare(queue, durable);
        channel.queueBind(queue, excange, "*");
        declared = true;
    }

    /**
     * Creates a task for message processing
     * @param message for processing
     * @throws Exception
     */
    private void processMessage(String message) throws Exception {
        Task newTask = new Task();
        newTask.setBeanName("messagePreProcessor");
        newTask.setMethodName("prepareMessage");
        newTask.setRawArguments(new Object[]{message});
        Message msg = new Message();
        msg.setMessage(message);
        try {
            String msgType = eventParser.parseHeader(message).getHeader().getMessageType().toString();
            msg.setType(msgType);
            newTask.setPriority(taskPriorityService.getPriorityForMessageType(msgType));
        } catch (Exception e) {
            log.error(e);
            newTask.setPriority(taskPriorityService.getPriorityForMessageType(""));
            msg.setType("");
        }

        newTask.setMessage(msg);
        taskManager.createPrepareTask(newTask);
    }

    private void sendAck(long deleveryTag) throws IOException {
        boolean multiple = false;
        channelManager.currentChannel().basicAck(deleveryTag, multiple);
    }

    private void pause(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            log.debug("Thread interrupted");
        }
    }

    private void beginTransactions() throws IOException {
        channelManager.currentChannel().txSelect();
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    private void commitTransactions() throws IOException {
        transactionManager.commit(transactionStatus);
        transactionStatus = null;
        channelManager.currentChannel().txCommit();
    }

    private void rollbackTransactions() {
        try {
            channelManager.currentChannel().txRollback();
        } catch (Exception e) {
            log.warn("Can not rollback transaction: " + e.getMessage());
        }
        if (transactionStatus != null) {
            try {
                transactionManager.rollback(transactionStatus);
            } catch (Exception e) {
                log.warn("Can not rollback transaction: " + e.getMessage());
            }
            transactionStatus = null;
        }
    }

    @Override
    public void setBeanName(String name) {
        this.threadName = name;
    }

    @Required
    public void setChannelManager(RabbitMqChannelManager channelManager) {
        this.channelManager = channelManager;
    }

    @Required
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Required
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Required
    public void setExcange(String excange) {
        this.excange = excange;
    }

    @Required
    public void setQueue(String queue) {
        this.queue = queue;
    }

    /**
     * Set value in seconds.
     */
    @Required
    public void setNoMessageDelay(long noMessageDelay) {
        this.noMessageDelay = noMessageDelay * ONE_SECOND;
    }

    /**
     * Set value in seconds.
     */
    @Required
    public void setErrorDelay(long errorDelay) {
        this.errorDelay = errorDelay * ONE_SECOND;
    }

    @Required
    public void setNotifyAdminExceptionHandler(NotifyAdminExceptionHandler notifyAdminExceptionHandler) {
        this.notifyAdminExceptionHandler = notifyAdminExceptionHandler;
    }

    @Required
    public void setExceptionNotificationStatusWatcher(
            ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher) {
        this.exceptionNotificationStatusWatcher = exceptionNotificationStatusWatcher;
    }

    @Required
    public void setEventParser(EventParser eventParser) {
        this.eventParser = eventParser;
    }

    @Required
    public void setTaskPriorityService(TaskPriorityService taskPriorityService) {
        this.taskPriorityService = taskPriorityService;
    }

	public void setHoptoadNotificationService(HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}

	public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}

}
