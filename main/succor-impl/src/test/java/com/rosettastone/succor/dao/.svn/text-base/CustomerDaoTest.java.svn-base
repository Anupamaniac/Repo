package com.rosettastone.succor.dao;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration({ "classpath:/hibernate-context.xml",
		"classpath:/properties-context.xml" })
public class CustomerDaoTest extends AbstractTestNGSpringContextTests {

	private static final int MAX_RESULT = 100;

	@Autowired
	@Qualifier("customerDao")
	private CustomerDAO customerDAO;

	@Autowired
	@Qualifier("bawSessionFactory")
	private SessionFactory sessionFactory;

	@Test(enabled = false)
	public void test() {
		List<CustomerEntity> customers = customerDAO.getByIdLang(
				"ec693194-4dd2-45bf-8816-4a6177575f48", "ITA");
		Assert.assertNotNull(customers);
	}

	@Test
	public void testAllUsers() {
		@SuppressWarnings("unchecked")
		List<CustomerEntity> list = sessionFactory.openSession()
				.createQuery("from CustomerEntity").setMaxResults(MAX_RESULT)
				.list();
		System.out.println("Size: " + list.size());
		for (CustomerEntity customer : list) {
			System.out.println(customer.getPrimaryKey().getLicense_guid()
					+ " => " + customer.getPrimaryKey().getLang());
		}
		System.out.println("Done");
	}
}
