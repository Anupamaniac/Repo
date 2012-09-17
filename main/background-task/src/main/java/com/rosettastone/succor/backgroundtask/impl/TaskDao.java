package com.rosettastone.succor.backgroundtask.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rosettastone.succor.backgroundtask.TaskStatisticBean;
import com.rosettastone.succor.backgroundtask.model.HistoryEntity;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.ParatureTaskTracker;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskExecutionStatus;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;

/**
 * DAO for tasks
 */
public class TaskDao extends HibernateDaoSupport {

    /**
     * Find first active task with specified type.
     * 
     * @param taskType
     *            - type of task
     * @return
     */
    public Task findFirstActiveTask(TaskType taskType) {

        DetachedCriteria criteria = null;
        @SuppressWarnings("unchecked")
        List<Task> tasks = null;
        criteria = generateCriteriaPriorityOrder(taskType);
        tasks = getHibernateTemplate().findByCriteria(criteria, 0, 1);
        Task result = null;
        if (!tasks.isEmpty()) {
            result = tasks.get(0);
            Hibernate.initialize(result.getHistory());
        }
        return result;
    }

    private DetachedCriteria generateCriteria(TaskType taskType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Task.class)
                .add(Restrictions.eq("status", TaskStatus.ACTIVE)).add(Restrictions.eq("type", taskType))
                .add(Restrictions.or(Restrictions.lt("nextRun", new Date()), Restrictions.isNull("nextRun")))
                .addOrder(Order.asc("id"));
        return criteria;
    }

    private DetachedCriteria generateCriteriaPriorityOrder(TaskType taskType) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Task.class)
                .add(Restrictions.eq("status", TaskStatus.ACTIVE)).add(Restrictions.eq("type", taskType))
                .add(Restrictions.isNull("nextRun"))
                .addOrder(Order.desc("priority")).addOrder(Order.desc("createdAt"));
        return criteria;
    }

    /**
     * Save message object in DB.
     * 
     * @param message
     */
    public void createMessage(Message message) {
        message.setCreatedAt(new Date());
        getHibernateTemplate().save(message);
    }

    /**
     * Store task object in DB.
     * 
     * @param task
     */
    public void create(Task task) {
        task.setCreatedAt(new Date());
        getHibernateTemplate().save(task);
    }

    /**
     * Update task object
     * 
     * @param task
     */
    public void update(Task task) {
        task.setUpdatedAt(new Date());
        getHibernateTemplate().update(task);
    }

    public void create(ParatureTaskTracker paratureTaskTracker) {
        getHibernateTemplate().save(paratureTaskTracker);
    }

    public void update(ParatureTaskTracker paratureTaskTracker) {
        getHibernateTemplate().update(paratureTaskTracker);
    }

    /**
     * Delete all tasks, messages, history that were created more than one month
     * ago.
     * 
     * @param boundDate
     * @param taskType
     */
    /*
     * public void deleteOldTasks(final Date boundDate, TaskType taskType) {
     * logger.debug("Delete old tasks type=" + taskType + " " + boundDate);
     * getHibernateTemplate()
     * .bulkUpdate("delete from HistoryEntity h where h.task in " +
     * "(from Task t where t.createdAt < ?)", boundDate); getHibernateTemplate()
     * .bulkUpdate(
     * "delete from ParatureTaskHistory pth where pth.paratureTaskTracker in " +
     * "(from ParatureTaskTracker ptt where ptt.task in (from Task t where t.createdAt < ?))"
     * , boundDate); getHibernateTemplate()
     * .bulkUpdate("delete from ParatureTaskTracker ptt where ptt.task in " +
     * "(from Task t where t.createdAt < ?)", boundDate);
     * getHibernateTemplate().
     * bulkUpdate("delete from Task t where t.createdAt < ?", boundDate);
     * getHibernateTemplate().executeWithNativeSession(new
     * HibernateCallback<Void>() {
     * 
     * @Override public Void doInHibernate(Session session) throws
     * HibernateException, SQLException { SQLQuery query =
     * session.createSQLQuery(
     * "delete from message where (created_at < ? or created_at is NULL) and id not in (select distinct t.message_id from task t)"
     * ); query.setDate(0, boundDate); int removed = query.executeUpdate();
     * logger.debug("Old messages were removed: " + removed); return null; } });
     * }
     */

    /**
     * Delete all tasks, messages, history, parature tasks and parature task
     * history that were created more than one month ago.
     * 
     * @param boundDate
     */

    public void deleteOldTasks(final Date boundDate) {
        logger.debug("Delete old data --- " + boundDate);
        getHibernateTemplate().bulkUpdate(
                "delete from HistoryEntity h where h.task in " + "(from Task t where t.createdAt < ?)", boundDate);
        getHibernateTemplate().bulkUpdate(
                "delete from ParatureTaskHistory pth where pth.paratureTaskTracker in "
                        + "(from ParatureTaskTracker ptt where ptt.task in (from Task t where t.createdAt < ?))",
                boundDate);
        getHibernateTemplate().bulkUpdate(
                "delete from ParatureTaskTracker ptt where ptt.task in " + "(from Task t where t.createdAt < ?)",
                boundDate);
        getHibernateTemplate().bulkUpdate("delete from Task t where t.createdAt < ?", boundDate);
        getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Void>() {
            @Override
            public Void doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session
                        .createSQLQuery("delete from message where (created_at < ? or created_at is NULL) and id not in (select distinct t.message_id from task t)");
                query.setDate(0, boundDate);
                int removed = query.executeUpdate();
                logger.debug("Old messages were removed: " + removed);
                return null;
            }
        });
    }

    public void resetNextRun() {
        getHibernateTemplate().bulkUpdate("update Task t set t.nextRun = null where t.nextRun is not null and t.nextRun < current_timestamp() and t.status = 'ACTIVE'");
    }
    /**
     * Returns last task id.
     * 
     * @return
     */
    public Long loadLastTaskId() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Task.class).addOrder(Order.desc("id"));
        @SuppressWarnings("unchecked")
        List<Task> result = getHibernateTemplate().findByCriteria(criteria, 0, 1);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0).getId();
    }

    /**
     * This method loads statistic for each type of task.
     * 
     * @return list of statistics beans
     */
    @SuppressWarnings("unchecked")
    public List<TaskStatisticBean> loadStatistic() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Task.class);
        criteria.setProjection(
                Projections.projectionList().add(Projections.rowCount()).add(Projections.groupProperty("status"))
                        .add(Projections.groupProperty("type"))).addOrder(Order.desc("type"));
        List<Object[]> queryResult = getHibernateTemplate().findByCriteria(criteria);
        List<TaskStatisticBean> statistic = new ArrayList<TaskStatisticBean>(queryResult.size());
        TaskStatisticBean currentBean = null;
        for (Object[] row : queryResult) {
            if (currentBean == null || !currentBean.getTaskType().equals(row[2])) {
                currentBean = new TaskStatisticBean();
                currentBean.setTaskType((TaskType) row[2]);
                currentBean.setStatistic(new EnumMap<TaskStatus, Long>(TaskStatus.class));
                statistic.add(currentBean);
            }
            currentBean.getStatistic().put((TaskStatus) row[1], (Long) row[0]);
        }
        return statistic;
    }

    /**
     * This method loads errors from DB sorted by execution date.
     * 
     * @param from
     * @param limit
     * @return
     */
    // "select task.message_id, task.id, task.type, history_entity.date, exception from task, history_entity where history_entity.status='FAILED' and task.id=history_entity.task_id order by date;";
    public List<HistoryEntity> loadTaskErrors(final Date from, int limit) {
        return getHibernateTemplate().execute(new FindErrors(from, limit));
    }

    /**
     * This methods returns first history object for specified task.
     * 
     * @param task
     * @return
     */
    public HistoryEntity loadFirstHistory(Task task) {
        List<HistoryEntity> list = getHibernateTemplate().execute(new FindFirst(task));
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    private class FindErrors implements HibernateCallback<List<HistoryEntity>> {

        private final int limit;
        private final Date from;

        public FindErrors(Date from, int limit) {
            this.from = from;
            this.limit = limit;
        }

        @Override
        public List<HistoryEntity> doInHibernate(Session session) throws HibernateException, SQLException {
            Criteria criteria = session.createCriteria(HistoryEntity.class, "history");
            criteria.add(Restrictions.eq("status", TaskExecutionStatus.FAILED));
            criteria.add(Restrictions.ge("date", from));
            criteria.setFetchMode("task", FetchMode.JOIN);
            criteria.setFetchMode("task.message", FetchMode.JOIN);
            criteria.addOrder(Order.desc("date"));
            criteria.setMaxResults(limit);
            return criteria.list();
        }
    }

    private class FindFirst implements HibernateCallback<List<HistoryEntity>> {

        private final Task task;

        public FindFirst(Task task) {
            this.task = task;
        }

        @Override
        public List<HistoryEntity> doInHibernate(Session session) throws HibernateException, SQLException {
            Criteria criteria = session.createCriteria(HistoryEntity.class, "history");
            criteria.add(Restrictions.eq("task", task));
            criteria.addOrder(Order.asc("date"));
            criteria.setMaxResults(1);
            return criteria.list();
        }
    }

}
