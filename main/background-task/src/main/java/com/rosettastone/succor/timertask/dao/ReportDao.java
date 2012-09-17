package com.rosettastone.succor.timertask.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rosettastone.succor.timertask.model.ReportEntity;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.timertask.model.StatisticBean;

public class ReportDao extends HibernateDaoSupport {

    public void createNewEntity(ReportEntityType type) {
        ReportEntity entity = new ReportEntity();
        entity.setType(type);
        entity.setDate(new Date());
        getHibernateTemplate().save(entity);
    }

    public StatisticBean generateStatistic(Date startDate, Date endDate) {
        StatisticBean bean = new StatisticBean();
        bean.setStartDate(startDate);
        bean.setEndDate(endDate);
        @SuppressWarnings("unchecked")
        List<Object[]> result = getHibernateTemplate().find(
                "select count(id), type from ReportEntity where date between ? and ? group by type",
                startDate, endDate);
        for (Object[] row : result) {
            Long counter = ((Long) row[0]);
            ReportEntityType type = (ReportEntityType) row[1];
            bean.put(type, counter);
        }

        @SuppressWarnings("unchecked")
        List<Long> postalHistory = getHibernateTemplate().find(
                "select count(id) from PostalHistory where processedAt between ? and ?", startDate, endDate);
        if (!postalHistory.isEmpty()) {
            Long counter = postalHistory.get(0);
            bean.put(ReportEntityType.POSTAL_TICKET, counter);
        }
        return bean;
    }
    
    public void cleanDb(Date endDate) {
        Date cleanDate = DateUtils.addDays(endDate, -1);
        getHibernateTemplate().bulkUpdate("delete from ReportEntity where date < ?", cleanDate);
    }
}
