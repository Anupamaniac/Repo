package com.rosettastone.succor.dao;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.rosettastone.succor.keepalive.KeepaliveController;

public class KeepaliveControllerDao {
	private HibernateTemplate hibernateTemplate;
	private HibernateTemplate bawHibernateTemplate;
	


	private static final Log LOG = LogFactory.getLog(KeepaliveController.class);

	/*
	 * public void validateMysqlDBConnection() throws SQLException {
	 * 		hibernateTemplate .executeWithNativeSession(new
	 * 		HibernateCallback<Boolean>() {
	 * 
	 * 		@Override public Boolean doInHibernate(Session session) throws
	 * 				SQLException { SQLQuery query = session.createSQLQuery("select 1");
	 * 			Object oneValue = query.uniqueResult(); return oneValue.equals(1); } });
	 * 		}
	 */
	/* @Transactional */
	public void validateMysqlDBConnection() throws SQLException {
		hibernateTemplate
				.executeWithNativeSession(new HibernateCallback<Boolean>() {
					@Override
					public Boolean doInHibernate(Session session)
							throws SQLException {
						LOG.debug("Checking Exception against Mysql");
						LOG.debug("Is connection valid==========="
								+ session.connection().isValid(1));
						if (!session.connection().isValid(1)) {
							throw new SQLException();
						}
						return false;
					}
				});
	}

	public void validateOracleDBConnection() throws SQLException {
		bawHibernateTemplate
				.executeWithNativeSession(new HibernateCallback<Boolean>() {
					@Override
					public Boolean doInHibernate(Session session)
							throws SQLException {
						LOG.debug("Checking Exception against Oracle");
						LOG.debug("Is connection valid==========="
								+ session.connection().isValid(1));
						if (!session.connection().isValid(1)) {
							throw new SQLException();
						}
						return false;
					}
				});
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	public HibernateTemplate getBawHibernateTemplate() {
		return bawHibernateTemplate;
	}

	public void setBawHibernateTemplate(HibernateTemplate bawHibernateTemplate) {
		this.bawHibernateTemplate = bawHibernateTemplate;
	}

}
