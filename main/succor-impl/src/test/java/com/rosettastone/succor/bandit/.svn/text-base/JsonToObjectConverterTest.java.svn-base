package com.rosettastone.succor.bandit;

import java.util.Date;
import java.util.Locale;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.CommunityAbsenceMessage;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.FirstCompletedStudioSessionMessage;
import com.rosettastone.succor.model.bandit.Header;
import com.rosettastone.succor.model.bandit.LevelCompletionMessage;
import com.rosettastone.succor.model.bandit.LevelsCompletedMessage;
import com.rosettastone.succor.model.bandit.License;
import com.rosettastone.succor.model.bandit.MessageType;
import com.rosettastone.succor.model.bandit.RWorldEncouragementMessage;
import com.rosettastone.succor.model.bandit.SessionCancelReason;
import com.rosettastone.succor.model.bandit.StudioReadinessMessage;
import com.rosettastone.succor.model.bandit.StudioReminderMessage;
import com.rosettastone.succor.model.bandit.StudioSessionCancellationMessage;
import com.rosettastone.succor.model.bandit.StudioSessionConfirmationMessage;

@Test
public class JsonToObjectConverterTest {

    private static final long CREATED_AT = 1281734508000L;
    private static final long SESSION_START_TIME = 1284486073000L;
    private static final Integer HOURS = 24;
    private static final long LEARNER_FIRST_SEEN = 1292274071000L;
    private static final Integer ATTENDANCE_ID = 433549839;
    private static final long CANCELLED_AT = 1292533272000L;
    private static final Integer DAYS = 5;

    public void testHeader() throws JSONException {
        JSONObject source = new JSONObject("{'message_type': 'level_completion_message', 'created_at': 1281734508, "
                + "'hostname': 'rshbgdev04', 'schema_version': 1}");
        JsonConverter<Header> converter = new JsonToObjectConverter<Header>(Header.class);
        Header header = converter.convert(source);

        Assert.assertEquals(header.getHostname(), "rshbgdev04");
        Assert.assertEquals(header.getMessageType(), MessageType.level_completion_message);
        Assert.assertEquals(header.getCreatedAt(), new Date(CREATED_AT));
    }

    public void testCustomer() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'guid':'ecdb1178-89f5-47e2-b1f5-bf8589619d3d',"
                + "'first_name':'Konstantin',"
                + "'last_name':'Zhirov',"
                + "'address_line_1':'25 street',"
                + "'address_line_2':'15 structure, 12',"
                + "'city':'New York',"
                + "'state_province':'NY',"
                + "'postal_code':'12345',"
                + "'country_iso':4,"
                + "'email':'kzhirov@rosettastone.com',"
                + "'contact_phone_number':'(333) 555555',"
                + "'contact_phone_country_code':'8',"
                + "'institutional': false,"
                + "'support_language_iso':'en-US',"
                + "'time_zone' : 'GMT+4'"
                + "}");

        JsonConverter<Customer> converter = new JsonToObjectConverter<Customer>(
                Customer.class);
        Customer customer = converter.convert(source);

        Assert.assertEquals(customer.getGuid(), "ecdb1178-89f5-47e2-b1f5-bf8589619d3d");
        Assert.assertEquals(customer.getFirstName(), "Konstantin");
        Assert.assertEquals(customer.getLastName(), "Zhirov");
        Assert.assertEquals(customer.getAddressLine1(), "25 street");
        Assert.assertEquals(customer.getAddressLine2(), "15 structure, 12");
        Assert.assertEquals(customer.getCity(), "New York");
        Assert.assertEquals(customer.getStateProvince(), "NY");
        Assert.assertEquals(customer.getPostalCode(), "12345");
        Assert.assertEquals(customer.getCountryIso(), "4");
        Assert.assertEquals(customer.getEmail(), "kzhirov@rosettastone.com");
        Assert.assertEquals(customer.getContactPhoneNumber(), "(333) 555555");
        Assert.assertEquals(customer.getContactPhoneCountryCode(), "8");
        Assert.assertEquals(customer.getInstitutional(), Boolean.FALSE);
        Assert.assertEquals(customer.getSupportLanguageIso(), Locale.US);
        Assert.assertEquals(customer.getTimeZone(), "GMT+4");
    }

    public void testLicense() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'test_license': true"
                + "}");

        JsonConverter<License> converter = new JsonToObjectConverter<License>(
                License.class);
        License license = converter.convert(source);
        Assert.assertEquals(license.getTestLicense(), Boolean.TRUE);
    }

    public void testLevelCompletion() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'course': 'SK-ARA-L1-NA-PE-NA-NA-Y-3',"
                + "'iso_language_code': 'ar',"
                + "'level': 1,"
                + "'guid': '11111111-1111-1111-1111-111111111111',"
                + "'rs_language_code': 'ARA'"
                + "}");
        JsonConverter<LevelCompletionMessage> converter = new JsonToObjectConverter<LevelCompletionMessage>(
                LevelCompletionMessage.class);
        LevelCompletionMessage message = converter.convert(source);

        Assert.assertEquals(message.getCourse(), "SK-ARA-L1-NA-PE-NA-NA-Y-3");
        Assert.assertEquals(message.getIsoLanguageCode(), "ar");
        Assert.assertEquals(message.getLevel(), Integer.valueOf(1));
        Assert.assertEquals(message.getGuid(), "11111111-1111-1111-1111-111111111111");
        Assert.assertEquals(message.getRsLanguageCode(), "ARA");
    }

    public void testLevelsCompletion() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'iso_language_code': 'en-US',"
                + "'levels': '1-3',"
                + "'guid': '11111111-1111-1111-1111-111111111111',"
                + "'rs_language_code': 'ENG'"
                + "}");
        JsonConverter<LevelsCompletedMessage> converter = new JsonToObjectConverter<LevelsCompletedMessage>(
                LevelsCompletedMessage.class);
        LevelsCompletedMessage message = converter.convert(source);

        Assert.assertEquals(message.getIsoLanguageCode(), "en-US");
        Assert.assertEquals(message.getLevels(), "1-3");
        Assert.assertEquals(message.getGuid(), "11111111-1111-1111-1111-111111111111");
        Assert.assertEquals(message.getRsLanguageCode(), "ENG");
    }

    public void testRWorldEncouragementMessage() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'guid': '11111111-1111-1111-1111-111111111111',"
                + "'iso_language_code': 'ar',"
                + "'rs_language_code': 'ARA'"
                + "}");
        JsonConverter<RWorldEncouragementMessage> converter =
            new JsonToObjectConverter<RWorldEncouragementMessage>(RWorldEncouragementMessage.class);
        RWorldEncouragementMessage message = converter.convert(source);

        Assert.assertEquals(message.getGuid(), "11111111-1111-1111-1111-111111111111");
        Assert.assertEquals(message.getIsoLanguageCode(), "ar");
        Assert.assertEquals(message.getRsLanguageCode(), "ARA");
    }

    public void testStudioReadinessMessage() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'rs_language_code': 'DEU',"
                + "'guid': 'c494a607-b21d-4b04-a6cd-7892hf29102s',"
                + "'course': 'SK-DEU-L2-NA-PE-NA-NA-Y-3',"
                + "'attended_studio_session': false,"
                + "'first_message_to_this_user': true,"
                + "'iso_language_code': 'de-DE',"
                + "'unit': 0,"
                + "'level': 2"
                + "}");
        JsonConverter<StudioReadinessMessage> converter =
            new JsonToObjectConverter<StudioReadinessMessage>(StudioReadinessMessage.class);
        StudioReadinessMessage message = converter.convert(source);

        Assert.assertEquals(message.getRsLanguageCode(), "DEU");
        Assert.assertEquals(message.getGuid(), "c494a607-b21d-4b04-a6cd-7892hf29102s");
        Assert.assertEquals(message.getCourse(), "SK-DEU-L2-NA-PE-NA-NA-Y-3");
        Assert.assertEquals(message.getAttendedStudioSession(), Boolean.FALSE);
        Assert.assertEquals(message.getFirstMessageToThisUser(), Boolean.TRUE);
        Assert.assertEquals(message.getIsoLanguageCode(), "de-DE");
        Assert.assertEquals(message.getUnit(), Integer.valueOf(0));
        Assert.assertEquals(message.getLevel(), Integer.valueOf(2));
    }

    public void testStudioReminderMessage() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'guid': 'c494a607-b21d-4b04-a6cd-7892hf29102s',"
                + "'unit': 0,"
                + "'iso_language_code': 'ar',"
                + "'rs_language_code': 'ARA',"
                + "'level': 1,"
                + "'session_start_time': 1284486073,"
                + "'hours': 24"
                + "}");
        JsonConverter<StudioReminderMessage> converter = new JsonToObjectConverter<StudioReminderMessage>(
                StudioReminderMessage.class);
        StudioReminderMessage message = converter.convert(source);

        Assert.assertEquals(message.getGuid(), "c494a607-b21d-4b04-a6cd-7892hf29102s");
        Assert.assertEquals(message.getUnit(), Integer.valueOf(0));
        Assert.assertEquals(message.getIsoLanguageCode(), "ar");
        Assert.assertEquals(message.getRsLanguageCode(), "ARA");
        Assert.assertEquals(message.getLevel(), Integer.valueOf(1));
        Assert.assertEquals(message.getSessionStartTime(), new Date(SESSION_START_TIME));
        Assert.assertEquals(message.getHours(), HOURS);

    }

    public void testStudioSessionCancellationMessage() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'reason': 'something',"
                + "'session_start_time': 1284486073,"
                + "'learner_first_seen_at': 1292274071,"
                + "'attendance_id': 433549839,"
                + "'level': 1,"
                + "'teacher_technical_problem': null,"
                + "'cancelled_by': 'Canceller',"
                + "'rs_language_code': 'ESP',"
                + "'guid': '11111111-1111-1111-1111-111111111111',"
                + "'cancelled_at': 1292533272,"
                + "'cancelled': true,"
                + "'unit': 1,"
                + "'teacher_first_seen_at': null,"
                + "'iso_language_code': 'es-419'"
                + "}");

        JsonConverter<StudioSessionCancellationMessage> converter =
            new JsonToObjectConverter<StudioSessionCancellationMessage>(StudioSessionCancellationMessage.class);
        StudioSessionCancellationMessage message = converter.convert(source);

        Assert.assertEquals(message.getReason(), SessionCancelReason.UNKNOWN);
        Assert.assertEquals(message.getSessionStartTime(), new Date(SESSION_START_TIME));
        Assert.assertEquals(message.getLearnerFirstSeenAt(), new Date(LEARNER_FIRST_SEEN));
        Assert.assertEquals(message.getAttendanceId(), ATTENDANCE_ID);
        Assert.assertEquals(message.getLevel(), Integer.valueOf(1));
        Assert.assertNull(message.getTeacherTechnicalProblem());
        Assert.assertEquals(message.getCancelledBy(), "Canceller");
        Assert.assertEquals(message.getRsLanguageCode(), "ESP");
        Assert.assertEquals(message.getGuid(), "11111111-1111-1111-1111-111111111111");
        Assert.assertEquals(message.getCancelledAt(), new Date(CANCELLED_AT));
        Assert.assertEquals(message.getCancelled(), Boolean.TRUE);
        Assert.assertEquals(message.getUnit(), Integer.valueOf(1));
        Assert.assertNull(message.getTeacherFirstSeenAt());
        Assert.assertEquals(message.getIsoLanguageCode(), "es-419");
    }

    public void testStudioSessionConfirmationMessage() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'unit': 1,"
                + "'attendance': '[[\"attended\",true],[\"audio_input_device\",null],[\"audio_output_device\",null], "
                + "[\"created_at\",\"2010-12-13T20:38:01Z\"],[\"eschool_session_id\",228387680],[\"first_seen_at"
                + "\",\"2010-12-10T20:38:01Z\"],[\"has_technical_problem\",false],[\"how_exited\",\"finished\"],"
                + "[\"id\",1008472637],[\"last_player_event_log\",null],[\"last_seen_at\",\"2010-12-10T21:38:01Z\"],"
                + "[\"session_enjoyed\",null],[\"student_id\",192593060],[\"studio_name\",null],"
                + "[\"updated_at\",\"2010-12-13T20:38:01Z\"]]',"
                + "'iso_language_code': 'es-419',"
                + "'level': 1,"
                + "'session_start_time': 1284486073,"
                + "'guid': '11111111-1111-1111-1111-111111111111',"
                + "'rs_language_code': 'ESP'"
                + "}");
        JsonConverter<StudioSessionConfirmationMessage> converter =
            new JsonToObjectConverter<StudioSessionConfirmationMessage>(StudioSessionConfirmationMessage.class);
        StudioSessionConfirmationMessage message = converter.convert(source);

        Assert.assertEquals(message.getUnit(), Integer.valueOf(1));
        Assert.assertEquals(message.getIsoLanguageCode(), "es-419");
        Assert.assertEquals(message.getLevel(), Integer.valueOf(1));
        Assert.assertEquals(message.getSessionStartTime(), new Date(SESSION_START_TIME));
        Assert.assertEquals(message.getGuid(), "11111111-1111-1111-1111-111111111111");
        Assert.assertEquals(message.getRsLanguageCode(), "ESP");
    }

    public void testFirstCompletedStudioSessionMessage() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'iso_language_code': 'ar',"
                + "'guid': '11111111-1111-1111-1111-111111111111',"
                + "'rs_language_code': 'ARA'"
                + "}");
        JsonConverter<FirstCompletedStudioSessionMessage> converter =
            new JsonToObjectConverter<FirstCompletedStudioSessionMessage>(FirstCompletedStudioSessionMessage.class);
        FirstCompletedStudioSessionMessage message = converter.convert(source);

        Assert.assertEquals(message.getIsoLanguageCode(), "ar");
        Assert.assertEquals(message.getGuid(), "11111111-1111-1111-1111-111111111111");
        Assert.assertEquals(message.getRsLanguageCode(), "ARA");
    }

    public void testCommunityAbsenceMessage() throws JSONException {
        JSONObject source = new JSONObject("{ "
                + "'days': 5,"
                + "'max_login': 1293827359,"
                + "'first_message_to_this_user': true,"
                + "'guid': '11111111-1111-1111-1111-111111111111'"
                + "}");
        JsonConverter<CommunityAbsenceMessage> converter =
            new JsonToObjectConverter<CommunityAbsenceMessage>(CommunityAbsenceMessage.class);
        CommunityAbsenceMessage message = converter.convert(source);

        Assert.assertEquals(message.getDays(), DAYS);
        Assert.assertEquals(message.getMaxLogin(), "1293827359");
        Assert.assertEquals(message.getFirstMessageToThisUser(), Boolean.TRUE);
        Assert.assertEquals(message.getGuid(), "11111111-1111-1111-1111-111111111111");
    }
}
