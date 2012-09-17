package com.rosettastone.succor.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * The {@code CustomerDAO} provides an access to {@code CustomerEntity} entities.
 *
 @see CustomerEntity
 */

public class CustomerDAO extends HibernateDaoSupport {

	public List<CustomerEntity> getCustomers() {
		return getHibernateTemplate().loadAll(CustomerEntity.class);
	}

    /**
     * Gets customers entities with provided params.
     *
     * @param id
     * @param langCode
     * @return list
     *
     @see CustomerPK
     */
	@SuppressWarnings("unchecked")
	public List<CustomerEntity> getByIdLang(String id, String langCode) {
		if (id == null) {
			return null;
		}

		DetachedCriteria criteria = DetachedCriteria
				.forClass(CustomerEntity.class);

		CustomerPK key = createKey(id, langCode);
		criteria.add(Restrictions.idEq(key));

		List<CustomerEntity> list = getHibernateTemplate().findByCriteria(
				criteria);

		if (list.size() > 0) {
			initEntities(list);
			return list;
		}
		return null;
	}

    /**
     * Creates {@code CustomerPK} object.
     * @param guid
     * @param lang
     * @return customerPK
     */
	private CustomerPK createKey(String guid, String lang) {
		CustomerPK customerPK = new CustomerPK();
		customerPK.setLang(lang);
		customerPK.setLicense_guid(guid);
		return customerPK;
	}

	/**
	 * Init customer entities.
	 * 
	 * @param list
	 */
	private void initEntities(List<CustomerEntity> list) {
		for (CustomerEntity ce : list) {
			Hibernate.initialize(ce);
		}
	}

}
