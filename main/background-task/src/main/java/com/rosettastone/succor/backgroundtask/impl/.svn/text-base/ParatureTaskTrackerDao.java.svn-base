package com.rosettastone.succor.backgroundtask.impl;

import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


import com.rosettastone.succor.backgroundtask.model.ParatureTaskHistory;
import com.rosettastone.succor.backgroundtask.model.ParatureTaskTracker;
import com.rosettastone.succor.backgroundtask.model.Task;



public class ParatureTaskTrackerDao extends HibernateDaoSupport {
	
	/**
     * Store task object in DB.
     * @param task
     */
	public void create(ParatureTaskTracker paratureTaskTracker) {
	        getHibernateTemplate().save(paratureTaskTracker);
	}

    /**
     * Update paratureTaskTracker object
     * @param paratureTaskTracker
     */
	public void update(ParatureTaskTracker paratureTaskTracker) {
	    getHibernateTemplate().update(paratureTaskTracker);
	}
	/**
     * Load ParatureTaskTracker object by id
     *
     * @param id
     * @return ParatureTaskTracker
     */
    public ParatureTaskTracker load(long id) {
        return getHibernateTemplate().load(ParatureTaskTracker.class, new Long(id));
    }

    /**
     * Load All ParatureTaskTracker objects
     *
     * @return list
     */
    public List<ParatureTaskTracker> loadAll() {
        return getHibernateTemplate().loadAll(ParatureTaskTracker.class);
    }
    
    
	public List<ParatureTaskTracker> loadParatureTaskTracker(Task task){
		List<ParatureTaskTracker> paratureTasks = getHibernateTemplate().find("select * from Parature_Task_Tracker where task_id=?",
                task.getId());
			return paratureTasks;
	}
}
