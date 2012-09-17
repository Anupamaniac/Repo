package com.rosettastone.succor.utils;

import com.rosettastone.succor.model.bandit.SessionCancelReason;
import com.rosettastone.succor.model.bandit.StudioReminderMessage;
import com.rosettastone.succor.model.bandit.StudioSatisfactionMessage;
import com.rosettastone.succor.model.bandit.StudioSessionCancellationMessage;
import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

@Test
public class MessageUtilsTest {

    @Test
    void testSessionReminder() {
        StudioReminderMessage msg = new StudioReminderMessage();
        msg.setSessionStartTime(new Date(System.currentTimeMillis() - 50000L));
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setSessionStartTime(new Date(System.currentTimeMillis() + 50000L));
        Assert.assertEquals(MessageUtils.isActual(msg), false);
    }

    @Test
    void testSessionCancellation() {
        StudioSessionCancellationMessage msg = new StudioSessionCancellationMessage();
        msg.setSessionStartTime(DateUtils.addMinutes(new Date(), -59));
        msg.setReason(SessionCancelReason.no_coach_yes_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), false);
        msg.setReason(SessionCancelReason.cancelled_before_session);
        Assert.assertEquals(MessageUtils.isActual(msg), false);
        msg.setReason(SessionCancelReason.cancelled_session_yes_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), false);
        msg.setReason(SessionCancelReason.cancelled_session_no_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), false);
        msg.setReason(SessionCancelReason.uncancelled_session_no_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), false);

        msg.setSessionStartTime(DateUtils.addMinutes(new Date(), -61));
        msg.setReason(SessionCancelReason.no_coach_yes_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setReason(SessionCancelReason.cancelled_before_session);
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setReason(SessionCancelReason.cancelled_session_yes_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setReason(SessionCancelReason.cancelled_session_no_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), false);
        msg.setReason(SessionCancelReason.uncancelled_session_no_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), false);

        msg.setSessionStartTime(DateUtils.addMinutes(new Date(), -24*60-1));
        msg.setReason(SessionCancelReason.no_coach_yes_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setReason(SessionCancelReason.cancelled_before_session);
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setReason(SessionCancelReason.cancelled_session_yes_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setReason(SessionCancelReason.cancelled_session_no_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setReason(SessionCancelReason.uncancelled_session_no_learner);
        Assert.assertEquals(MessageUtils.isActual(msg), true);

    }

    @Test
    void testOther() {
        StudioSatisfactionMessage msg = new StudioSatisfactionMessage();
        msg.setSessionStartTime(new Date(System.currentTimeMillis() - 50000L));
        Assert.assertEquals(MessageUtils.isActual(msg), true);
        msg.setSessionStartTime(new Date(System.currentTimeMillis() + 50000L));
        Assert.assertEquals(MessageUtils.isActual(msg), true);
    }

}
