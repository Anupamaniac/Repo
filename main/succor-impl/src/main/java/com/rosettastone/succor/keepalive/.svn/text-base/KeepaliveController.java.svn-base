package com.rosettastone.succor.keepalive;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rosettastone.succor.backgroundtask.impl.ExceptionNotificationStatusWatcher;
import com.rosettastone.succor.dao.KeepaliveControllerDao;

/**
 * The {@code KeepaliveController} provides the ability to view some info.
 */

@Controller
public class KeepaliveController {
	
	private static final Log LOG = LogFactory.getLog(KeepaliveController.class);

    private Collection<Thread> backgroundThreads;
    private ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher;
    private KeepaliveControllerDao keepAliveControllerDao; 

    

    /**
     * Provides the ability to view threads info.
     * @return string
     */
    @RequestMapping("/keepalive")
    @ResponseBody
    public String checkstatus() {
    
    	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
    	Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
    	for(Thread t:threadArray){
			LOG.debug("THREAD NAME ***** "+t.getName()+" ***** STATE ***** "+t.getState());
		}
    	
        StringBuilder buffer = new StringBuilder();
        for (Thread thread : backgroundThreads) {
            if (!thread.isAlive()) {
                buffer.append("Thread " + thread.getName() + " is not alive.");
            }
        } 
        checkOracle(buffer);
        checkMySql(buffer);
        
         
        /*if (exceptionNotificationStatusWatcher.hashExceptions()) {
            buffer.append("There are unresolved exceptions, message processing may stopped.\n");
        }*/
        
        String status="";
        if (buffer.length() > 0) {
            status = "ERROR:" + buffer.toString();
        } else {
            status = "OK";
        }
        return status;
    }
    
    public void checkOracle(StringBuilder buffer){
    	try{
			/*Boolean isConnected = */keepAliveControllerDao.validateOracleDBConnection();
//			if(!isConnected){
//				buffer.append(" Connection to oracle DB is lost.");
//			}
		}
		catch (Throwable t) {
			LOG.debug("Exception in Throwable exception"+t);
			t.printStackTrace();
			buffer.append(" Connection to oracle DB is lost.");
		}
    }
    public void checkMySql(StringBuilder buffer){
    	 try {
 			/*Boolean isConnected = */keepAliveControllerDao.validateMysqlDBConnection();
// 			if(!isConnected){
// 				buffer.append(" Connection to mysql DB is lost.");
// 			}
 			}
 			catch (Throwable t) {
 				LOG.debug("Exception in Throwable exception"+t);
 				t.printStackTrace();
 				buffer.append(" Connection to mysql DB is lost.");
 			}
    }

    @Required
    public void setBackgroundThreads(Collection<Thread> backgroundThreads) {
        this.backgroundThreads = backgroundThreads;
    }

    @Required
    public void setExceptionNotificationStatusWatcher(
            ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher) {
        this.exceptionNotificationStatusWatcher = exceptionNotificationStatusWatcher;
    }

	public void setKeepAliveControllerDao(KeepaliveControllerDao keepAliveControllerDao) {
		this.keepAliveControllerDao = keepAliveControllerDao;
	}

	public KeepaliveControllerDao getKeepAliveControllerDao() {
		return keepAliveControllerDao;
	}
  
}
