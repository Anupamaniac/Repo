package com.rosettastone.succor.mdp;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.StringUtils;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.bandit.EventParser;
import com.rosettastone.succor.bandit.ProductNameGenerator;
import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.License;
import com.rosettastone.succor.model.bandit.Message;
import com.rosettastone.succor.model.bandit.MessageType;
import com.rosettastone.succor.model.bandit.StudioReadinessMessage;
import com.rosettastone.succor.model.bandit.StudioReminderMessage;
import com.rosettastone.succor.model.bandit.StudioSessionCancellationMessage;
import com.rosettastone.succor.model.bandit.UpdateMessage;
import com.rosettastone.succor.service.CustomerSynchronizationService;
import com.rosettastone.succor.service.EmailService;
import com.rosettastone.succor.spring.BanditEventScopeEvent;
import com.rosettastone.succor.spring.BanditEventScopeEvent.EventType;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.utils.MessageUtils;

/**
 * The {@code RabbitMqMessageProcessor} processes the JSON message.
 */
public class MessagePreProcessor extends ApplicationObjectSupport {

	private Log log = LogFactory.getLog(MessagePreProcessor.class);

	private CustomerSynchronizationService synchronizationService;

	private ProductNameGenerator productNameGenerator = new ProductNameGenerator();

	private ReportDao reportDao;
	private EventParser eventParser;
	private TaskManager taskManager;

	private Set<MessageType> messagesIgnoreContactOption;

	public static String KLE_RS_CODE = "KLE";
	public static String JLE_RS_CODE = "JLE";

	public static final String rsKidPattern = ".rskid[0-9]+$";

	// TODO rename?
	public static String REFLEX_PRODUCT_NAME = "REFLEX";

	public MessagePreProcessor() {
		messagesIgnoreContactOption = EnumSet.of(
				MessageType.studio_session_cancellation_message,
				MessageType.studio_reminder_message);
	}

	/**
	 * Main method for message processing.
	 * 
	 * @param message
	 *            JSON message for processing
	 */
	public void prepareMessage(String message) {
		if (log.isDebugEnabled()) {
			log.debug("Received: " + message + "\n");
		}
		Event event = null;
		Customer customer = null;
		try {
			event = eventParser.parse(message);
			customer = event.getCustomer();
			if (!checkEvent(event)) {
				return;
			}

			getApplicationContext().publishEvent(
					new BanditEventScopeEvent(event,
							EventType.PROCESSING_STARTED));

			String productName;
			if (isKLE(event.getMessage())) {
				productName = REFLEX_PRODUCT_NAME;
				customer.setSupportLanguageIso(Locale.KOREAN);
				event.getInternalSuccorData().setSupportLocale(Locale.KOREAN);
			} else if (isJLE(event.getMessage())) {
				productName = REFLEX_PRODUCT_NAME;
				customer.setSupportLanguageIso(Locale.JAPANESE);
				event.getInternalSuccorData().setSupportLocale(Locale.JAPANESE);
			} else {
				String rsLangCode = event.getMessage().getRsLanguageCode();
				License license = event.getLicense();
				Boolean isInstitutional = customer.getInstitutional();
				if (isInstitutional) {
					productName = "INST";
				} else {
					productName = productNameGenerator.generateProductName(
							license, rsLangCode);
				}
			}
			if (productName != null) {
				List<String> productNames = new ArrayList<String>(1);
				productNames.add(productName);
				customer.setProductNames(productNames);
			} else if (!event.getInternalSuccorData().getTestWorkLocally()) {
				synchronizationService.synchronizeWithBAW(event);
			}
			if (customer.getContactPhoneCountryCode() != null
					&& customer.getContactPhoneNumber() != null)
				customer.setContactPhoneNumber(customer
						.getContactPhoneCountryCode()
						+ customer.getContactPhoneNumber());
			if (event.getInternalSuccorData().getTestWorkLocally()) {
				log.debug("Testing locally, skip synchronization");
			} else {
				if (StringUtils.hasLength(customer.getGuid())
						|| customer.getEmail() != null) {
					// synchronizationService.syncronizeWithParature(event);/*Parature
					// dereferencing*/
					syncronizeKidWithoutParature(event);/*
														 * Parature
														 * dereferencing
														 */
				} else {
					throw new ApplicationException(
							"Customer has no GUID and Email");
				}
			}
			if(!conformToWC(event)){
				return;
			}
			// if ( !event.getLicense().getGrandfathered()){
			if (event.getMessage().getClass()
					.equals(StudioReminderMessage.class)
					&& ((StudioReminderMessage) event.getMessage())
							.getNumberOfSeats() != null
					&& ((StudioReminderMessage) event.getMessage())
							.getNumberOfSeats() == 1) {
				((StudioReminderMessage) event.getMessage()).setSolo(true);
				event.getLicense().setGrandfathered(true);
			}
			if (event.getMessage().getClass()
					.equals(StudioSessionCancellationMessage.class)
					&& ((StudioSessionCancellationMessage) event.getMessage())
							.getNumberOfSeats() != null
					&& ((StudioSessionCancellationMessage) event.getMessage())
							.getNumberOfSeats() == 1) {

				((StudioSessionCancellationMessage) event.getMessage())
						.setSolo(true);
				event.getLicense().setGrandfathered(true);
			}
			// }
			if (isUpgradeCustomer(event)) {
				reportDao.createNewEntity(ReportEntityType.MESSAGE_SKIPPED);
			} else {
				createRulesTask(event);
			}
		} finally {
			if (customer != null) {
				getApplicationContext().publishEvent(
						new BanditEventScopeEvent(event,
								EventType.PROCESSING_FINISHED));
			}
		}
	}

	private boolean conformToWC(Event event) {
		// TODO Auto-generated method stub
		if (event.getMessage().getClass()
				.equals(StudioReminderMessage.class)
				&& ((StudioReminderMessage) event.getMessage())
						.getNumberOfSeats() != null
				&& ((StudioReminderMessage) event.getMessage())
						.getNumberOfSeats() == 1 && !event.getLicense().hasSoloRight()) {
			log.debug("Skip message that is not actual, solo right absent.");
			return false;
		}
		if (event.getMessage().getClass()
				.equals(StudioSessionCancellationMessage.class)
				&& ((StudioSessionCancellationMessage) event.getMessage())
						.getNumberOfSeats() != null
				&& ((StudioSessionCancellationMessage) event.getMessage())
						.getNumberOfSeats() == 1 && !event.getLicense().hasSoloRight()) {
			log.debug("Skip message that is not actual, solo right absent.");
			return false;
		}
		return true;
	}

	private void syncronizeKidWithoutParature(Event event) {
		Customer customer = event.getCustomer();
		boolean isKid = isKid(customer.getEmail());
		customer.setKid(isKid);
		if (customer.isKid()) {
			customer.setEmail(getParentEmail(customer.getEmail()));
		}

	}

	private static boolean isKid(String email) {
		if (email != null) {
			email = email.trim();
			Pattern p = Pattern.compile(EmailService.rsKidPattern);
			Matcher m = p.matcher(email);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}

	private String getParentEmail(String email) {
		email = email.trim();
		return email.replaceAll(rsKidPattern, "");
	}

	private void createRulesTask(Event event) {
		Task newTask = new Task();
		newTask.setBeanName("rabbitMqMessageProcessor");
		newTask.setMethodName("processMessage");
		newTask.setRawArguments(new Object[] { event });
		taskManager.createRules(newTask);
	}

	private boolean checkEvent(Event event) {
		if (event.getInternalSuccorData().getSupportLocale() == null) {
			log.debug("Skip customer with no support language. ");
			return false;
		}

		if (!MessageUtils.isActual(event.getMessage())) {
			log.debug("Skip message that is not actual.");
			return false;
		}
		if (event.getMessage() instanceof StudioReminderMessage
				&& ((StudioReminderMessage) event.getMessage())
						.getNumberOfSeats() != null
				&& ((StudioReminderMessage) event.getMessage())
						.getNumberOfSeats() == 0) {
			log.debug("Skip message that is not actual in StudioReminderMessage.");
			return false;
		}

		if (event.getMessage() instanceof StudioSessionCancellationMessage
				&& ((StudioSessionCancellationMessage) event.getMessage())
						.getNumberOfSeats() != null
				&& ((StudioSessionCancellationMessage) event.getMessage())
						.getNumberOfSeats() == 0) {
			log.debug("Skip message that is not actual in StudioSessionCancellationMessage.");
			return false;
		}
		return true;
	}

	/**
	 * Check if we need to Process this event.
	 * 
	 * @param event
	 * @return
	 */
	private boolean isUpgradeCustomer(Event event) {
		// upgrade customers fix
		if (event.getMessage() instanceof UpdateMessage) {
			Date maxUpdated = ((UpdateMessage) event.getMessage())
					.getMaxUpdatedAt();
			Date upgradedAt = event.getLicense().getUpgradedAt();
			if (maxUpdated != null && upgradedAt != null
					&& maxUpdated.before(upgradedAt)) {
				log.info("Skip upgrade customer");
				return true;
			}
		}
		return false;
	}

	private boolean isKLE(Message message) {
		return isRsCode(message, KLE_RS_CODE);
	}

	private boolean isJLE(Message message) {
		return isRsCode(message, JLE_RS_CODE);
	}

	private boolean isRsCode(Message message, String rsLangCode) {
		if (message.getRsLanguageCode() == null) {
			return false;
		}
		return message.getRsLanguageCode().equals(rsLangCode);
	}

	@Required
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	@Required
	public void setSynchronizationService(
			CustomerSynchronizationService synchronizationService) {
		this.synchronizationService = synchronizationService;
	}

	@Required
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Required
	public void setEventParser(EventParser eventParser) {
		this.eventParser = eventParser;
	}
}
