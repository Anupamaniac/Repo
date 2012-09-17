package com.rosettastone.succor.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * The {@code DiscoveryCallDao} provides an access to {@code DiscoveryCallEntity} entities.
 *
 @see DiscoveryCallEntity
 */

public class DiscoveryCallDao extends HibernateDaoSupport {

    /**
     * Loads {@code DiscoveryCallEntity} entities.
     * @param date
     * @return list
     *
     @see DiscoveryCallPK
     */
    public List<DiscoveryCallEntity> loadForDate(Long date) {
        DetachedCriteria criteria = DetachedCriteria.forClass(DiscoveryCallEntity.class);
        criteria.add(Restrictions.ge("id.orderedOn", date));
        criteria.add(Restrictions.isNotNull("id.email"));
        List<DiscoveryCallEntity> list = getHibernateTemplate().findByCriteria(criteria);
        return list;
    }

}
