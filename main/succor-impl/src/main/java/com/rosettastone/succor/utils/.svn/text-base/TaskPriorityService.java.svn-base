package com.rosettastone.succor.utils;

import com.rosettastone.succor.dao.EventMessageTypeDao;
import com.rosettastone.succor.model.bandit.MessageType;
import com.rosettastone.succor.web.model.EventMessageType;

/**
 * User: mixim
 * Date: 16.09.11
 */
public class TaskPriorityService {

    public static final short MAX_PRIORITY = 4;
    public static final short MIN_PRIORITY = 0;

    private EventMessageTypeDao eventMessageTypeDao;

    public void setEventMessageTypeDao(EventMessageTypeDao eventMessageTypeDao) {
        this.eventMessageTypeDao = eventMessageTypeDao;
    }

    public short getTaskPriority(MessageType messageType){
        switch (messageType){
            case studio_reminder_message:
                return MAX_PRIORITY;
            case studio_session_cancellation_message:
                return MAX_PRIORITY;
            default:
                return MIN_PRIORITY;
        }
    }

    /**
     * Gets priority for message type.
     *
     * @param messageType
     * @return
     */
    public short getPriorityForMessageType(String messageType){
        short priority = MIN_PRIORITY;

        EventMessageType eventMessageType = null;
        eventMessageType = eventMessageTypeDao.getMessagePriorityByType(messageType);
        if(eventMessageType!=null){
            priority = eventMessageType.getPriority();
        }
        return priority;
    }

}
