package com.rosettastone.succor.timertask.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rosettastone.succor.timertask.model.PostalHistory;
import com.rosettastone.succor.timertask.model.PostalHistoryStatus;

/**
 * DAO for working with PostalHistory model objects
 * @see PostalHistory
 */
public class PostalHistoryDao extends HibernateDaoSupport {

    /**
     * Save PostalHistory entity to DB
     * @param history
     */
    public void create(PostalHistory history) {
        getHibernateTemplate().save(history);
    }

    /**
     * Update all objects from specified collection.
     * @param collection
     */
    public void update(Collection<PostalHistory> collection) {
        // getHibernateTemplate().bulkUpdate("update PostalHistory ps set ps.status=? where ps.id in (1)",
        // PostalHistoryStatus.PROCESSED);
        for (PostalHistory history : collection) {
            getHibernateTemplate().update(history);
        }
    }

    /**
     * Load unprocessed PostalHistory entities for specified locale.
     * @param locale
     * @return List of PostalHistory
     */
    public List<PostalHistory> loadUnprocessed(Locale locale) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PostalHistory.class).add(
                Restrictions.eq("status", PostalHistoryStatus.UNPROCESSED));

        if (locale.equals(Locale.ENGLISH)) {
            // For English department we send ALL locales which are not Spanish and not Jpanaese and not Korean, so
            // SELECT .. WHERE ... AND supportLocale != 'ja' AND supportLocale != 'es' AND supportLocale != 'ko'
            criteria.add(Restrictions.ne("supportLocale", Locale.JAPANESE.getLanguage())).
                    add(Restrictions.ne("supportLocale", "es")).
                    add(Restrictions.ne("supportLocale", "ko"));
        } else {
            criteria.add(Restrictions.eq("supportLocale", locale.getLanguage()));
        }

        @SuppressWarnings("unchecked")
        List<PostalHistory> postalHistoryList = getHibernateTemplate().findByCriteria(criteria);
        for (PostalHistory postalHistory : postalHistoryList) {
            Hibernate.initialize(postalHistory);
        }
        return postalHistoryList;
    }

    /**
     * Remove objects from database that older than specified date.
     * @param date
     * @return number of removed objects
     */
    public int removeProcessedOlderThan(Date date) {
        DetachedCriteria criteria = DetachedCriteria.forClass(PostalHistory.class)
                .add(Restrictions.lt("createdAt", date)).add(Restrictions.eq("status", PostalHistoryStatus.PROCESSED));

        @SuppressWarnings("unchecked")
        List<PostalHistory> postalHistoryList = getHibernateTemplate().findByCriteria(criteria);
        getHibernateTemplate().deleteAll(postalHistoryList);
        return postalHistoryList.size();
    }
}
