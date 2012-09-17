package com.rosettastone.succor.bandit;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.rosettastone.succor.exception.InvalidJsonException;
import com.rosettastone.succor.model.bandit.ClaimedExtensionsMessage;
import com.rosettastone.succor.model.bandit.CommunityAbsenceMessage;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.ExpiringSubscriptionMessage;
import com.rosettastone.succor.model.bandit.FirstCompletedStudioSessionMessage;
import com.rosettastone.succor.model.bandit.FollowUpWithLearnerMessage;
import com.rosettastone.succor.model.bandit.Header;
import com.rosettastone.succor.model.bandit.InitialProfileSetupMessage;
import com.rosettastone.succor.model.bandit.InternalSuccorData;
import com.rosettastone.succor.model.bandit.LevelCompletionMessage;
import com.rosettastone.succor.model.bandit.LevelsCompletedMessage;
import com.rosettastone.succor.model.bandit.License;
import com.rosettastone.succor.model.bandit.Message;
import com.rosettastone.succor.model.bandit.MessageType;
import com.rosettastone.succor.model.bandit.RWorldEncouragementMessage;
import com.rosettastone.succor.model.bandit.StudioReadinessMessage;
import com.rosettastone.succor.model.bandit.StudioReminderMessage;
import com.rosettastone.succor.model.bandit.StudioSatisfactionMessage;
import com.rosettastone.succor.model.bandit.StudioSessionCancellationMessage;
import com.rosettastone.succor.model.bandit.StudioSessionConfirmationMessage;

/**
 * Main parser for JSON messages. We parse JSON message by sections: header,
 * license, customer
 */
public class EventParser {

    private static final String SEPARATOR = "_";
    private static final String FIRST = "first";

    private JsonConverter<Header> headerConverter;
    private JsonConverter<License> licenseConverter;
    private JsonConverter<Customer> customerConverter;
    private Map<MessageType, JsonConverter<? extends Message>> messageConverterMap;

    public EventParser() {
        // create base sections converters
        headerConverter = new JsonToObjectConverter<Header>(Header.class);
        licenseConverter = new LicenseConverter();
        customerConverter = new JsonToObjectConverter<Customer>(Customer.class);

        // create parsers for each type of message
        messageConverterMap = new EnumMap<MessageType, JsonConverter<? extends Message>>(MessageType.class);
        messageConverterMap.put(MessageType.claimed_extensions_message,
                new JsonToObjectConverter<ClaimedExtensionsMessage>(ClaimedExtensionsMessage.class));
        messageConverterMap
                .put(MessageType.first_completed_studio_session_message,
                        new JsonToObjectConverter<FirstCompletedStudioSessionMessage>(
                                FirstCompletedStudioSessionMessage.class));
        messageConverterMap.put(MessageType.level_completion_message,
                new JsonToObjectConverter<LevelCompletionMessage>(LevelCompletionMessage.class));
        messageConverterMap.put(MessageType.levels_completed_message,
                new JsonToObjectConverter<LevelsCompletedMessage>(LevelsCompletedMessage.class));
        messageConverterMap.put(MessageType.not_logged_in_for_days, new JsonToObjectConverter<CommunityAbsenceMessage>(
                CommunityAbsenceMessage.class));
        messageConverterMap.put(MessageType.rosetta_world_encouragement_message,
                new JsonToObjectConverter<RWorldEncouragementMessage>(RWorldEncouragementMessage.class));
        messageConverterMap.put(MessageType.studio_readiness_message,
                new JsonToObjectConverter<StudioReadinessMessage>(StudioReadinessMessage.class));
        messageConverterMap.put(MessageType.studio_reminder_message, new JsonToObjectConverter<StudioReminderMessage>(
                StudioReminderMessage.class));
        messageConverterMap.put(MessageType.studio_session_cancellation_message,
                new JsonToObjectConverter<StudioSessionCancellationMessage>(StudioSessionCancellationMessage.class));
        messageConverterMap.put(MessageType.studio_session_confirmation_message,
                new JsonToObjectConverter<StudioSessionConfirmationMessage>(StudioSessionConfirmationMessage.class));
        messageConverterMap.put(MessageType.attendance_needs_followup_message,
                new JsonToObjectConverter<FollowUpWithLearnerMessage>(FollowUpWithLearnerMessage.class));
        messageConverterMap.put(MessageType.studio_satisfaction_message,
                new JsonToObjectConverter<StudioSatisfactionMessage>(StudioSatisfactionMessage.class));
        messageConverterMap.put(MessageType.expiring_subscription_message,
                new JsonToObjectConverter<ExpiringSubscriptionMessage>(ExpiringSubscriptionMessage.class));
        messageConverterMap.put(MessageType.initial_profile_setup_message,
                new JsonToObjectConverter<InitialProfileSetupMessage>(InitialProfileSetupMessage.class));
        messageConverterMap.put(MessageType.UNKNOWN, new JsonToObjectConverter<Message>(Message.class));
    }

    public Event parseHeader(String jsonSource) {
        try {
            Event event = new Event(jsonSource);

            JSONObject json = new JSONObject(jsonSource);

            event.setHeader(headerConverter.convert(json.getJSONObject("header")));
            return event;
        } catch (JSONException e) {
            throw new InvalidJsonException("Invalid message", e);
        }

    }

    public Event parse(String jsonSource) {
        try {
            Event event = new Event(jsonSource);

            JSONObject json = new JSONObject(jsonSource);

            event.setHeader(headerConverter.convert(json.getJSONObject("header")));

            if (json.has("license_data")) {
                event.setLicense(licenseConverter.convert(json.getJSONObject("license_data")));
            }

            event.setCustomer(customerConverter.convert(json.getJSONObject("customer_data")));

            JsonConverter<? extends Message> messageConverter = messageConverterMap.get(event.getHeader()
                    .getMessageType());
            event.setMessage(messageConverter.convert(json.getJSONObject("message")));

            populateInternalSuccorInfo(event, json);
            return event;
        } catch (JSONException e) {
            throw new InvalidJsonException("Invalid message", e);
        }
    }

    /**
     * Calculate and set some fields in InternalSuccorData.
     * 
     * @param event
     * @param json
     * @throws JSONException
     */
    private void populateInternalSuccorInfo(Event event, JSONObject json) throws JSONException {
        generateActionAsKey(event);
        generateSupportLocale(event);
        processTestData(event, json);
    }

    /**
     * Calculate action key using message type and some additional fields from
     * concrete message class and set into InternalSuccorData.
     * 
     * @see InternalSuccorData
     * @param event
     *            - event message
     */
    private void generateActionAsKey(Event event) {
        String key;
        Message message = event.getMessage();
        String messageType = event.getHeader().getMessageType().name();
        if (message instanceof LevelCompletionMessage) {
            key = messageType + SEPARATOR + ((LevelCompletionMessage) message).getLevel();
        } else if (message instanceof LevelsCompletedMessage) {
            key = messageType + SEPARATOR + ((LevelsCompletedMessage) message).getLevels();
        } else if (message instanceof StudioReadinessMessage
                && ((StudioReadinessMessage) message).getFirstMessageToThisUser()) {
            key = FIRST + SEPARATOR + messageType;
        } else if (message instanceof CommunityAbsenceMessage) {
            key = messageType + SEPARATOR + ((CommunityAbsenceMessage) message).getDays();
            CommunityAbsenceMessage communityAbsenceMessage = ((CommunityAbsenceMessage) message);
            if (communityAbsenceMessage.getFirstMessageToThisUser() != null
                    && communityAbsenceMessage.getFirstMessageToThisUser()) {
                key += SEPARATOR + FIRST;
            }
        } else if (message instanceof StudioSessionCancellationMessage) {
            key = messageType + SEPARATOR + ((StudioSessionCancellationMessage) message).getReason().name();
        } else {
            key = messageType;
        }
        event.getInternalSuccorData().setActionAsKey(key);
    }

    /**
     * Calculate current support language from 2 json fields and set into
     * InternalSuccorData supportLocale field We use just Language code.
     * 
     * @see InternalSuccorData
     * @param event
     *            - event message
     */
    private void generateSupportLocale(Event event) {
        Locale supportLanguage = event.getCustomer().getSupportLanguageIso();
        if (supportLanguage == null) {
            supportLanguage = event.getCustomer().getLastViewedLocale();
        }
        Locale supportLocale = Locale.ENGLISH;
        if (supportLanguage != null) {
            supportLocale = new Locale(supportLanguage.getLanguage());
        }
        event.getInternalSuccorData().setSupportLocale(supportLocale);
    }

    private void processTestData(Event event, JSONObject json) throws JSONException {
        if (json.has("test")) {
            JSONObject testData = json.getJSONObject("test");
            event.getInternalSuccorData().setTestWorkLocally(testData.optBoolean("workLocally", Boolean.FALSE));
        }
    }
}
