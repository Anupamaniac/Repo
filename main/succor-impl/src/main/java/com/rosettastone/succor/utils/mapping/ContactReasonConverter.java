package com.rosettastone.succor.utils.mapping;

import javax.annotation.PostConstruct;

import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.parature.ContactReasonType;

/**
 * Convert customer action into contact reason.
 */
public class ContactReasonConverter extends MapConverter<ContactReasonType> {

    @PostConstruct
    public void init() {
        add("level_completion_message_1", ContactReasonType.TOTALE_1_COMPLETION);
        add("level_completion_message_2", ContactReasonType.TOTALE_2_COMPLETION);
        add("level_completion_message_3", ContactReasonType.TOTALE_3_COMPLETION);
        add("level_completion_message_4", ContactReasonType.TOTALE_4_COMPLETION);
        add("level_completion_message_5", ContactReasonType.TOTALE_5_COMPLETION);
        add("completion_of_level1_unit1_corelesson1", ContactReasonType.COMPLETION_OF_LEVEL1_UNIT1_CORELESSON1);
        add("completed_first_studio_session", ContactReasonType.FIRST_STUDIO_COMPLETITION);
        add("levels_completed_message_1-3", ContactReasonType.COMPLETION_OF_LEVELS_123);
        add("levels_completed_message_1-5", ContactReasonType.COMPLETION_OF_LEVELS_12345);
        add("not_logged_in_for_days_30_first", ContactReasonType.LOG_BACK_IN_ENC_30DAYS_FIRST);
        add("not_logged_in_for_days_30", ContactReasonType.LOG_BACK_IN_ENC_30DAYS);
        add("rosetta_world_encouragement_message", ContactReasonType.ROSETTA_WORLD_ENCOURAGEMENT_MESSAGE);
        add("studio_session_cancellation_message_uncancelled_session_no_learner",
                ContactReasonType.SESSION_CANCELLATION_UNCANCELLED_NO_LEARNER);
        add("studio_session_cancellation_message_cancelled_session_no_learner",
                ContactReasonType.SESSION_CANCELLATION_CANCELLED_NO_LEARNER);
        add("studio_session_cancellation_message_no_coach_yes_learner", ContactReasonType.SESSION_CANCELLATION_NO_COACH);
        add("studio_session_cancellation_message_cancelled_before_session",
                ContactReasonType.SESSION_CANCELLATION_BEFORE_SESSION);
        add("studio_session_cancellation_message_cancelled_session_yes_learner",
                ContactReasonType.SESSION_CANCELLATION_YES_LEARNER);
        add("claimed_extensions_message", ContactReasonType.CLAIMED_EXTENSIONS_MESSAGE);
        add("first_studio_readiness_message", ContactReasonType.STUDIO_SIGNUP_ENC);
        add("first_completed_studio_session_message", ContactReasonType.FIRST_STUDIO_COMPLETITION);
        add("first_completed_studio_session_message", ContactReasonType.FIRST_STUDIO_COMPLETITION);
        add("attendance_needs_followup_message", ContactReasonType.POOR_EXPERIENCE_FOLLOWUP);
        add("studio_satisfaction_message", ContactReasonType.STUDIO_SATISFACTION);
        add("expiring_subscription_message", ContactReasonType.EXPIRING_SUBSCRIPTION);
        add("discovery_call_message", ContactReasonType.DISCOVERY_CALL);
        add("initial_profile_setup_message", ContactReasonType.INITIAL_PROFILE_SETUP);
    }

    public ContactReasonType convert(Event event) {
        return super.convert(event.getInternalSuccorData().getActionAsKey());
    }
}
