package com.rosettastone.succor.backgroundtask.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.service.HoptoadNotificationService;
/**
 * Is is implementation of thread that execute tasks.
 *
 * By default we can process tasks in parallel (at the same time). I.e one task per executor.
 * But sometimes we need to stop all executors threads and do some work (send report for example).
 * It uses ReadWriteLock for synchronization.
 *
 * @see com.rosettastone.succor.timertask.EmailReportTimer
 * @see ReadWriteLock
 */
public class TaskExecutorThread extends Thread implements BeanNameAware {

    private static final Log LOG = LogFactory.getLog(TaskExecutorThread.class);

    /**
     * Object that control loading, running, reporting of task execution.
     */
    private TaskExecutor executor;

    private volatile boolean running;

    private String beanName;

    private ReadWriteLock readWriteLock;
    private HoptoadNotificationService hoptoadNotificationService;

    /**
     * This method calls by spring after context initialization,
     * It is configurating and starting task executor thread.
     */
    @PostConstruct
    public void init() {
        LOG.info("init");
        this.setDaemon(true);
        this.setName(beanName);
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
     * It calls by spring when application going down (stop or undeploy).
     */
    @PreDestroy
    public void shutdown() {
        running = false;
        this.interrupt();
        try {
            this.join();
        } catch (InterruptedException e) {
        	if(hoptoadNotificationService!=null)
        	hoptoadNotificationService.notifyingHopToad(e);
            LOG.info("Unexpected error", e);
        }
        LOG.info("shutdown");
    }

    /**
     * Main thread loop
     *
     */
    public void run() {
        LOG.info("Started");
        running = true;
        while (running) {
            Lock readLock = readWriteLock.readLock();
            readLock.lock();
            long loopDelay;
            try {
                loopDelay = executor.execute();
                if (loopDelay > 0) {
                    Thread.sleep(loopDelay);
                }
            } catch (Exception e) {
            	if(hoptoadNotificationService!=null)
            	hoptoadNotificationService.notifyingHopToad(e);
                LOG.info("Executor Interrupted", e);
                break;
            } finally {
            	try{
                readLock.unlock();
            	}catch(Throwable t){
            		if(hoptoadNotificationService!=null)
                    	hoptoadNotificationService.notifyingHopToad(t);
                        LOG.info("finally failed", t);
            	}
            }
        }
        LOG.info("Stopped");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Required
    public void setExecutor(TaskExecutor executor) {
        this.executor = executor;
    }

    @Required
    public void setReadWriteLock(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

	public void setHoptoadNotificationService(HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}

	public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}
}
