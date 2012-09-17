package com.rosettastone.succor.dao;

import com.rosettastone.succor.web.model.EventMessageType;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

//import org.hibernate.Hibernate;

/**
 * User: mixim
 * Date: 26.09.11
 *
 * The {@code MessagePriorityDao} provides an access to {@code MessagePriority} entities.
 *
 */

public class EventMessageTypeDao extends HibernateDaoSupport {

    /**
     * Gets message priority for message type.
     *
     * @param messageType
     * @return eventMessageType
     */
    public EventMessageType getMessagePriorityByType(String messageType){
        EventMessageType result = null;
        DetachedCriteria criteria = null;
        criteria = generateCriteria(messageType);
        @SuppressWarnings("unchecked")
        List<EventMessageType> messageTypes = null;
        messageTypes = getHibernateTemplate().findByCriteria(criteria);
        if (!messageTypes.isEmpty()) {
            result = messageTypes.get(0);
        }
        return result;
    }

    /**
     * Generate criteria for search of message type's priority .
     *
     * @param messageType
     * @return
     */
    private DetachedCriteria generateCriteria(String messageType){
        DetachedCriteria criteria = DetachedCriteria.forClass(EventMessageType.class)
                .add(Restrictions.eq("messageType", messageType));
        return criteria;
    }

}
