package com.rosettastone.succor.timertask.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rosettastone.succor.timertask.model.EmailReportHistory;

public class EmailReportHistoryDao extends HibernateDaoSupport {

    public void create(EmailReportHistory history) {
        history.setDate(new Date());
        getHibernateTemplate().save(history);
    }

    @SuppressWarnings("unchecked")
    public EmailReportHistory loadLast() {
        DetachedCriteria criteria = DetachedCriteria.forClass(EmailReportHistory.class)
            .addOrder(Order.desc("id"));
        List<EmailReportHistory> result = getHibernateTemplate().findByCriteria(criteria, 0, 1);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
