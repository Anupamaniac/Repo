insert into product(name, long_name) values ("L1",  "Totale - L1");
insert into product(name, long_name) values ("L2",  "Totale - L2");
insert into product(name, long_name) values ("L3",  "Totale - L3");
insert into product(name, long_name) values ("L4",  "Totale - L4");
insert into product(name, long_name) values ("L5",  "Totale - L5");
insert into product(name, long_name) values ("S2",  "Totale - S2");
insert into product(name, long_name) values ("S3",  "Totale - S3");
insert into product(name, long_name) values ("S5",  "Totale - S5");
insert into product(name, long_name) values ("TOT", "Totale");
insert into product(name, long_name) values ("U1",  "Totale - U1");
insert into product(name, long_name) values ("INST",  "Institutional TOTALe");
insert into product(name, long_name) values ("REFLEX",  "Reflex");


insert into variable(id, name) values(1, "level");
insert into variable(id, name) values(2, "levels");
insert into variable(id, name) values(3, "days");
insert into variable(id, name) values(4, "reason");
insert into variable(id, name) values(5, "firstMessageToThisUser");
insert into variable(id, name) values(6, "hours");
insert into variable(id, name) values(7, "satisfied");
insert into variable(id, name) values(8, "attendedStudioSession");


insert into message_type(id, message_type, class_name, priority)
    values(1, "studio_reminder_message", "StudioReminderMessage", 4);

insert into message_type(id, message_type, class_name, priority)
    values(2, "studio_session_cancellation_message", "StudioSessionCancellationMessage",4);

insert into message_type(id, message_type, class_name)
    values(3, "level_completion_message", "LevelCompletionMessage");

insert into message_type(id, message_type, class_name)
    values(4, "levels_completed_message", "LevelsCompletedMessage");

insert into message_type(id, message_type, class_name)
    values(5, "not_logged_in_for_days", "CommunityAbsenceMessage");

insert into message_type(id, message_type, class_name)
    values(6, "first_completed_studio_session_message", "FirstCompletedStudioSessionMessage");

insert into message_type(id, message_type, class_name)
    values(7, "claimed_extensions_message", "ClaimedExtensionsMessage");

insert into message_type(id, message_type, class_name)
    values(8, "rosetta_world_encouragement_message", "RWorldEncouragementMessage");

insert into message_type(id, message_type, class_name)
    values(9, "studio_readiness_message", "StudioReadinessMessage");

insert into message_type(id, message_type, class_name)
    values(10, "attendance_needs_followup_message", "FollowUpWithLearnerMessage");

insert into message_type(id, message_type, class_name)
    values(11, "studio_satisfaction_message", "StudioSatisfactionMessage");

insert into message_type(id, message_type, class_name)
    values(12, "expiring_subscription_message", "ExpiringSubscriptionMessage");

insert into message_type(id, message_type, class_name)
    values(13, "discovery_call_message", "DiscoveryCallMessage");

insert into message_type(id, message_type, class_name)
    values(14, "studio_session_confirmation_message", "StudioSessionConfirmationMessage");

insert into message_type(id, message_type, class_name)
    values(15, "initial_profile_setup_message", "InitialProfileSetupMessage");

insert into event(id, name, message_type_id )
    values(1, "Completion of Level 1", 3);

insert into event(id, name, message_type_id )
    values(2, "Completion of Level 2", 3);

insert into event(id, name, message_type_id )
    values(3, "Completion of Level 3", 3);

insert into event(id, name, message_type_id )
    values(4, "Completion of Level 4", 3);

insert into event(id, name, message_type_id )
    values(5, "Completion of Level 5", 3);

insert into event(id, name, message_type_id )
    values(8, "Completion of Levels 1,2,3", 4);

insert into event(id, name, message_type_id )
    values(9, "Completion of Levels 1,2,3,4,5" ,4);

insert into event(id, name, message_type_id )
    values(10, "Not logged back in after first 30 days", 5);

insert into event(id, name, message_type_id )
    values(11, "Not logged back in after 30 days except first", 5);

insert into event(id, name, message_type_id )
    values(13, "Completed first Studio Session", 6);

insert into event(id, name, message_type_id )
    values(15, "Session Reminder - 24 hours before session", 1);

insert into event(id, name, message_type_id )
    values(16, "Session Cancellation - Coach No-show - A Learner Attended", 2);

insert into event(id, name, message_type_id )
    values(17, "Session Cancellation - Coach Cancels - no learners show", 2);

insert into event(id, name, message_type_id )
    values(18, "Session Cancellation - Cancelled prior to session - Learner was signed up", 2);

insert into event(id, name, message_type_id )
    values(19, "Session Cancellation - Cancelled during Session - Learner attended", 2);

insert into event(id, name, message_type_id )
    values(20, "Learner No Show - At least one learner does not attend and session is not cancelled", 2);

insert into event(id, name, message_type_id )
    values(21, "Start their subscription Claim Online Rights", 7);

insert into event(id, name, message_type_id )
    values(23, "Completed their first 3 Core Lessons", 8);

insert into event(id, name, message_type_id )
    values(25, "Ready for first studio session", 9);

insert into event(id, name, message_type_id )
    values(26, "Follow Up With Learner", 10);

insert into event(id, name, message_type_id )
    values(27, "Studio Satisfaction", 11);

insert into event(id, name, message_type_id )
    values(28, "Expiring Subscription", 12);

insert into event(id, name, message_type_id )
    values(31, "Not logged back in after 10 days", 5);

insert into event(id, name, message_type_id )
    values(32, "Session Reminder - 6 hours before session", 1);

insert into event(id, name, message_type_id )
    values(33, "Discovery Call", 13);

insert into event(id, name, message_type_id )
    values(34, "Initial Profile Setup", 15);

insert into variable_values(id, event_id, variable_id, value) values (1, 1, 1, "1");
insert into variable_values(id, event_id, variable_id, value) values (2, 2, 1, "2");
insert into variable_values(id, event_id, variable_id, value) values (3, 3, 1, "3");
insert into variable_values(id, event_id, variable_id, value) values (4, 4, 1, "4");
insert into variable_values(id, event_id, variable_id, value) values (5, 5, 1, "5");
insert into variable_values(id, event_id, variable_id, value) values (6, 8, 2, "1-3");
insert into variable_values(id, event_id, variable_id, value) values (7, 9, 2, "1-5");
insert into variable_values(id, event_id, variable_id, value) values (8, 10, 3, "30");
insert into variable_values(id, event_id, variable_id, value) values (9, 10, 5, "true");
insert into variable_values(id, event_id, variable_id, value) values (10, 11, 3, "30");
insert into variable_values(id, event_id, variable_id, value) values (11, 11, 5, "false");
insert into variable_values(id, event_id, variable_id, value) values (12, 16, 4, "SessionCancelReason.no_coach_yes_learner");
insert into variable_values(id, event_id, variable_id, value) values (13, 17, 4, "SessionCancelReason.cancelled_session_no_learner");
insert into variable_values(id, event_id, variable_id, value) values (14, 18, 4, "SessionCancelReason.cancelled_before_session");
insert into variable_values(id, event_id, variable_id, value) values (15, 19, 4, "SessionCancelReason.cancelled_session_yes_learner");
insert into variable_values(id, event_id, variable_id, value) values (16, 20, 4, "SessionCancelReason.uncancelled_session_no_learner");
insert into variable_values(id, event_id, variable_id, value) values (17, 25, 5, "true");
insert into variable_values(id, event_id, variable_id, value) values (18, 31, 3, "10");
insert into variable_values(id, event_id, variable_id, value) values (19, 15, 6, "24");
insert into variable_values(id, event_id, variable_id, value) values (20, 32, 6, "6");
insert into variable_values(id, event_id, variable_id, value) values (21, 27, 7, "false");
insert into variable_values(id, event_id, variable_id, value) values (22, 25, 8, "false");


insert into template_variable(id, name, display_name) values(1,  "event.customer.firstName", "First Name");
insert into template_variable(id, name, display_name) values(2,  "event.customer.lastName", "Last Name");
insert into template_variable(id, name, display_name) values(3,  "language", "Language");
insert into template_variable(id, name, display_name) values(4,  "event.message.level", "Level");
insert into template_variable(id, name, display_name) values(5,  "event.message.unit", "Unit");
insert into template_variable(id, name, display_name) values(6,  "sessionStartTime", "Session Start Time");
insert into template_variable(id, name, display_name) values(7,  "event.customer.parentFirstName", "Parent First Name");
insert into template_variable(id, name, display_name) values(8,  "event.customer.parentLastName", "Parent Last Name");
insert into template_variable(id, name, display_name) values(9,  "event.message.sessionId?c", "SessionId");
insert into template_variable(id, name, display_name) values(10, "event.message.studentId?c", "StudentId");
insert into template_variable(id, name, display_name) values(11, "event.message.attendanceId?c", "AttendanceId");
insert into template_variable(id, name, display_name) values(12, "event.customer.communityFirstName", "Community First Name");
insert into template_variable(id, name, display_name) values(13, "event.customer.communityLastName", "Community Last Name");
