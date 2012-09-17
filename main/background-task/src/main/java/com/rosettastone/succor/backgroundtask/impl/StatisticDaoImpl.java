package com.rosettastone.succor.backgroundtask.impl;

import com.rosettastone.succor.backgroundtask.MessageStatistic;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * DAO for global statistic (processed, unprocessed, processed with errors messages).
 */
public class StatisticDaoImpl extends HibernateDaoSupport implements StatisticDao {

    private static final Log logger = LogFactory.getLog(StatisticDaoImpl.class);

    /**
     * Calculate and return statistic for messages that appear after specified date.
     * @param afterDate
     * @return statistic object
     */
    @SuppressWarnings("unchecked")
    public MessageStatistic loadMessageStatistic(Date afterDate) {
        logger.debug("Load Message Statistic");
        updateStatistic(afterDate);
        return loadStatistic();
    }

    /**
     * Updates statistic temporary table
     * @param date
     */
    @Transactional
    public void updateStatistic(final Date date) {
       logger.debug("Update Statistic");
        getHibernateTemplate().execute(new UpdateStatistic(date));
    }

    /**
     * Load statistic from temporary table
     */
    @Transactional(readOnly = true)
    public MessageStatistic loadStatistic() {
        return getHibernateTemplate().execute(new LoadStatistic());
    }

    /**
     * Helper class for loading statistic
     */
    private class LoadStatistic implements HibernateCallback<MessageStatistic> {

        @Override
        public MessageStatistic doInHibernate(Session session) throws HibernateException, SQLException {
            final MessageStatistic statistic = new MessageStatistic();
            SQLQuery query = session.createSQLQuery("select count(distinct message_id) as val from msg_statistic " +
                    "where status = ? AND tasks = " +
                    "(select count(id) from task where task.message_id = msg_statistic.message_id group by message_id);");
            query.setString(0, TaskStatus.COMPLETED.name());
            query.addScalar("val", Hibernate.LONG);
            statistic.setCompleted(getAnswer(query.list()));
            query = session.createSQLQuery("select count(distinct message_id) as val from msg_statistic where status = ?;");
            query.setString(0, TaskStatus.CANCELLED.name());
            query.addScalar("val", Hibernate.LONG);
            statistic.setCancelled(getAnswer(query.list()));
            query = session.createSQLQuery("select count(distinct message_id) as val from msg_statistic;");
            query.addScalar("val", Hibernate.LONG);
            statistic.setTotal(getAnswer(query.list()));
            statistic.setActive(statistic.getTotal() - statistic.getCancelled() - statistic.getCompleted());
            return statistic;
        }


        private long getAnswer(List list) {
            if (list.isEmpty()) {
                return 0;
            }
            return (Long) list.get(0);
        }
    }

    /**
     * Helper class for updating statistic table
     */
    private class UpdateStatistic implements HibernateCallback<Void> {

        private final Date date;

        public UpdateStatistic(Date date) {
            this.date = date;
        }

        @Override
        public Void doInHibernate(Session session) throws HibernateException, SQLException {
            SQLQuery query;
            try {
                query = session.createSQLQuery("delete from msg_statistic;");
                query.executeUpdate();
            } catch(Exception e) {
                logger.info(e);
            }//temporary
            query = session.createSQLQuery("create temporary table IF NOT EXISTS msg_statistic (\n" +
                    "  message_id bigint,\n" +
                    "  status VARCHAR(255),\n" +
                    "  tasks bigint,\n" +
                    "  primary key(message_id, status)\n" +
                    ") ENGINE=InnoDB;");
            query.executeUpdate();

            query = session.createSQLQuery("insert into msg_statistic(message_id, status, tasks) " +
                    "select message_id, status, count(status) from task, message " +
                    "where message_id = message.id and message.created_at >= ? group by message_id, status;");
            query.setDate(0, date);
            int res = query.executeUpdate();
            logger.debug("Insert statistic to table " + res);
            return null;
        }

    }

}


