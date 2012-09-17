package com.rosettastone.succor.backgroundtask.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rosettastone.succor.TestUtil;
import com.rosettastone.succor.backgroundtask.TaskStatisticBean;
import com.rosettastone.succor.backgroundtask.model.HistoryEntity;
import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskExecutionStatus;
import com.rosettastone.succor.backgroundtask.model.TaskStatus;
import com.rosettastone.succor.backgroundtask.model.TaskType;

@Test(enabled = false)
@ContextConfiguration({ "classpath:/backgroundtask-context.xml",
		"classpath:/test-context.xml" })
@TransactionConfiguration(transactionManager = "taskTransactionManager")
public class TaskDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	public static final Long FIRST_ACTIVE_EMAIL_TASK_ID = 3L;
	public static final Long FIRST_ACTIVE_EMAIL_TASK_HISTORY_SIZE = 3L;
	public static final int MESSAGE_ONE_TASKS_SIZE = 3;
	public static final Long LAST_TASK_ID = 4L;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private DataSource taskDataSource;

	@Autowired
	private HibernateTemplate taskHibernateTemplate;

	@BeforeMethod
	public void initDb() throws Exception {
		TestUtil.initDb("classpath:/task-dataset.xml", taskDataSource,
				applicationContext);
	}

	public void testConfiguration() {
		Assert.assertNotNull(taskDao);
	}

	public void testCreate() {
		Task task = new Task();
		task.setArguments("test arguments");
		task.setBeanName("test bean");
		task.setMethodName("test method");
		task.setStatus(TaskStatus.COMPLETED);
		task.setType(TaskType.EMAIL);
		task.setMessage(taskHibernateTemplate.load(Message.class, 1L));
		taskDao.create(task);
	}

	public void testUpdate() {
		Task task = taskHibernateTemplate
				.execute(new HibernateCallback<Task>() {
					@Override
					public Task doInHibernate(Session session) {
						Task task = (Task) session.load(Task.class, 1L);
						Hibernate.initialize(task);
						return task;
					}
				});
		task.setNextRun(new Date());
		HistoryEntity history = new HistoryEntity();
		history.setDate(new Date());
		history.setException("Test exception");
		history.setStacktrace("Test stacktrace");
		history.setStatus(TaskExecutionStatus.FAILED);
		task.getHistory().add(history);
		taskDao.update(task);
	}

	public void testFindFirstActiveTask() {
		Task task = taskDao.findFirstActiveTask(TaskType.EMAIL);
		Assert.assertNotNull(task);
		Assert.assertEquals(task.getId(), FIRST_ACTIVE_EMAIL_TASK_ID);
		Assert.assertTrue(task.getHistory().size() == FIRST_ACTIVE_EMAIL_TASK_HISTORY_SIZE);
	}

	public void testDeleteOldTasks() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2010, 8, 2, 1, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		taskDao.deleteOldTasks(calendar.getTime());
		int taskCount = taskHibernateTemplate
				.execute(new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session) {
						@SuppressWarnings("unchecked")
						List<Task> tasks = (List<Task>) session.createQuery(
								"from Task").list();
						return tasks.size();
					}
				});
		Assert.assertEquals(taskCount, 2);
		List<Message> messages = taskHibernateTemplate.loadAll(Message.class);
		Assert.assertEquals(messages.size(), 1);
	}

	public void testCreateMessage() {
		Message message = new Message();
		message.setMessage("Test message");
		taskDao.createMessage(message);
	}

	public void testMessageMapping() {
		Message message = taskHibernateTemplate.load(Message.class, 1L);
		Assert.assertEquals(message.getTasks().size(), MESSAGE_ONE_TASKS_SIZE);
		message = taskHibernateTemplate.load(Message.class, 2L);
		Assert.assertEquals(message.getTasks().size(), 1);
	}

	@Test(expectedExceptions = DataIntegrityViolationException.class, enabled = false)
	public void testCreateMessageNullMessage() {
		Message message = new Message();
		taskDao.createMessage(message);
	}

	public void testLoadLoadLastTaskId() {
		Long id = taskDao.loadLastTaskId();
		Assert.assertEquals(id, LAST_TASK_ID);
	}

	public void testLoadStatistic() {
		List<TaskStatisticBean> statistic = taskDao.loadStatistic();
		Assert.assertNotNull(statistic);
		Assert.assertEquals(statistic.size(), 2);
		for (TaskStatisticBean taskStatisticBean : statistic) {
			if (TaskType.EMAIL.equals(taskStatisticBean.getTaskType())) {
				Assert.assertEquals(
						taskStatisticBean.getStatistic().get(TaskStatus.ACTIVE),
						Long.valueOf(2L));
				Assert.assertEquals(
						taskStatisticBean.getStatistic().get(
								TaskStatus.COMPLETED), Long.valueOf(1L));
			} else if (TaskType.PARATURE
					.equals(taskStatisticBean.getTaskType())) {
				Assert.assertEquals(
						taskStatisticBean.getStatistic().get(TaskStatus.ACTIVE),
						Long.valueOf(1L));
			} else {
				Assert.fail("Unexpected task type: "
						+ taskStatisticBean.getTaskType());
			}
		}
	}

	public void testLoadErrors() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2010, 6, 1, 00, 00, 00);
		List<HistoryEntity> list = taskDao.loadTaskErrors(calendar.getTime(),
				100);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 3);
	}

	public void testLoadFirst() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2010, 8, 1, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Task task = new Task();
		task.setId(3L);
		HistoryEntity first = taskDao.loadFirstHistory(task);
		Assert.assertNotNull(first);
		Assert.assertEquals(first.getDate(), calendar.getTime());
	}

}
