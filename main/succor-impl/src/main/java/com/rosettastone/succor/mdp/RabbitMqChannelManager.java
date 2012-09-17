package com.rosettastone.succor.mdp;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rosettastone.succor.service.HoptoadNotificationService;

/**
 * The {@code RabbitMqChannelManager} provides the functionality for managing of rabbitmq channel.
 */

public class RabbitMqChannelManager {

    private final Log log = LogFactory.getLog(RabbitMqChannelManager.class);

    private ConnectionFactory connectionFactory;

    private Connection connection;

    private Channel channel;

    private String host; 
    
    private int port;
    
    private HoptoadNotificationService hoptoadNotificationService;

    @PreDestroy
    public void destroy() throws IOException {
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }

    /**
     *
     * @return channel
     * @throws IOException
     */
    public Channel currentChannel() throws IOException {
        if (channel == null) {
            channel = createChannel();
        }
        return channel;
    }

    private Channel createChannel() throws IOException {
    	
        if (connection == null || !connection.isOpen()) {
        	connection = connectionFactory.newConnection(host, port); 
        }
        connection.addShutdownListener(new ShutdownListener() {
            public void shutdownCompleted(ShutdownSignalException cause)
            {
            	if (cause.isHardError())
            	  {
            	    Connection conn = (Connection)cause.getReference();
            	    if (!cause.isInitiatedByApplication())
            	    {
            	      Object reason = cause.getReason();
            	      log.debug(reason.toString()+""+conn.toString());
            	      if(hoptoadNotificationService!=null)
            	      hoptoadNotificationService.notifyingHopToad(cause);
            	    }
            	 
            	  } else {
            	    Channel ch = (Channel)cause.getReference();
            	    log.debug(""+ch);
            	    if(hoptoadNotificationService!=null)
            	    hoptoadNotificationService.notifyingHopToad(cause);
            	  } 
            }
        });

        return connection.createChannel();
    }

    public void closeCurrentChannel() {
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
            	if(hoptoadNotificationService!=null)
            	hoptoadNotificationService.notifyingHopToad(e);
                log.warn("Can not close channel: " + e.getMessage());
            }
            channel = null;
        }
    }

    @Required
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Required
    public void setHost(String host) {
    	this.host = host; 
    }

    @Required
    public void setPort(int port) {
    	this.port = port; 
    }

	public void setHoptoadNotificationService(HoptoadNotificationService hoptoadNotificationService) {
		this.hoptoadNotificationService = hoptoadNotificationService;
	}

	public HoptoadNotificationService getHoptoadNotificationService() {
		return hoptoadNotificationService;
	}
}
