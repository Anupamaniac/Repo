package com.rosettastone.succor.web.dao;

import com.rosettastone.succor.web.model.Event;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * The {@code EventDao} provides methods for working with {@code Event}.
 */

public class EventDao extends HibernateDaoSupport {

    /**
     * Load Event object by id
     *
     * @param id
     * @return event
     */
    public Event load(long id) {
        return getHibernateTemplate().load(Event.class, new Long(id));
    }

    /**
     * Load All Event objects
     *
     * @return list
     */
    public List<Event> loadAll() {
        return getHibernateTemplate().loadAll(Event.class);
    }

    /**
     * Save Event object to DB
     *
     * @return
     */
    public void create(Event event) {
        getHibernateTemplate().save(event);
    }

    /**
     * Update Event object in DB
     *
     * @return
     */
    public void update(Event event) {
        getHibernateTemplate().update(event);
    }

    /**
     * Remove Event object from DB by id
     *
     * @return
     */
    public void remove(long id) {
        Event event = getHibernateTemplate().load(Event.class, new Long(id));
        if (event != null) {
            getHibernateTemplate().delete(event);
        }
    }

}
