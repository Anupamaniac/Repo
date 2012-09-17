package com.rosettastone.succor.utils;

import com.rosettastone.succor.model.bandit.Message;
import com.rosettastone.succor.model.bandit.MessageLevelUnitStartTime;
import com.rosettastone.succor.model.bandit.StudioReminderMessage;
import com.rosettastone.succor.model.bandit.StudioSessionCancellationMessage;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;


public class MessageUtils {

    /**
     * We have some time sensitive triggers like session reminder and session cancellation.
     * In production sometime we have some delay when we process such messages and we can send
     * some email with reminder in a few days after it was consumed from the queue.
     * Basically we need to have some configurable parameter in the app, where we can define the time.
     * Session reminder, Session cancellation,  do not do anything after the session supposed to be started.
     *
     * Example:
     * Session supposed to be started at 4-00PM today, application consumed it today at 5AM,
     * started processed it at 4-02PM. In this case app should  just skip this message.
     *
     * Triggers to apply logic not to process message:
     * Time of message > session start time:
     *      6 Hr studio session reminder
     *      24 Hr studio session reminder
     * Time of message > session start time + 1 hour:
     *      Coach no show
     *      Coach Cancelled prior
     *      Coach Cancelled during
     *
     * Time of message > session start time + 24 hour:
     *      Coach cancels / learner no show
     *      Learner no show
     *
     * @param message
     * @return false if we should skip message
     */
    public static boolean isActual(Message message) {
        if (!(message instanceof MessageLevelUnitStartTime)) {
            return true;
        }
        MessageLevelUnitStartTime timedMessage = (MessageLevelUnitStartTime) message;
        if (timedMessage.getSessionStartTime() == null) {
            return true;
        }

        if (message instanceof StudioReminderMessage) {
            return timedMessage.getSessionStartTime().after(new Date());
        }

        if (message instanceof StudioSessionCancellationMessage) {
            StudioSessionCancellationMessage cancellationMessage = (StudioSessionCancellationMessage) message;
            int hours = 0;
            switch (cancellationMessage.getReason()) {
                case no_coach_yes_learner:
                case cancelled_before_session:
                case cancelled_session_yes_learner:
                    hours = 1;
                    break;
                case cancelled_session_no_learner:
                case uncancelled_session_no_learner:
                    hours = 24;
                    break;
                default:
                    break;
            }
            Date date = DateUtils.addHours(new Date(), -1*hours);
            return timedMessage.getSessionStartTime().after(date);
        }
        return true;
    }
}
