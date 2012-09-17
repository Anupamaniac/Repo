package com.rosettastone.succor.model.bandit;

public enum SessionCancelReason {
    UNKNOWN,
    no_coach_yes_learner,
    cancelled_before_session,
    cancelled_session_no_learner,
    cancelled_session_yes_learner,
    uncancelled_session_no_learner
}
