package com.rosettastone.succor.backgroundtask.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rosettastone.succor.backgroundtask.model.ParatureTaskHistory;
import com.rosettastone.succor.backgroundtask.model.ParatureTaskTracker;
import com.rosettastone.succor.exception.ParatureErrorCode;

public class ParatureTaskTrackerDaoTest {
	private static ParatureTaskTrackerDao paratureTaskTrackerDao;
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {
				"hibernate-context.xml"
                });
		paratureTaskTrackerDao = ctx.getBean(ParatureTaskTrackerDao.class);
		ParatureTaskTracker paratureTaskTracker = paratureTaskTrackerDao.loadAll().get(1);
		System.out.println(paratureTaskTracker.getParatureAction());
		ParatureTaskHistory paratureTaskHistory = new ParatureTaskHistory(); 
		paratureTaskHistory.setParatureErrorCode(ParatureErrorCode.BAD_GATEWAY_502);
		paratureTaskTracker.getParatureTaskHistory().add(paratureTaskHistory);
		paratureTaskTrackerDao.update(paratureTaskTracker);
	}
	public static void setParatureTaskTrackerDao(ParatureTaskTrackerDao paratureTaskTrackerDao) {
		ParatureTaskTrackerDaoTest.paratureTaskTrackerDao = paratureTaskTrackerDao;
	}
	public static ParatureTaskTrackerDao getParatureTaskTrackerDao() {
		return paratureTaskTrackerDao;
	}
}
