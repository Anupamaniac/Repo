package com.rosettastone.succor.service.sms;

import com.rosettastone.succor.exception.SMSException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Nikolay Sazonov
 * Date: 5/3/11
 * Implementation of SMS API specified for CellTrust mobile provider.
 */
public class CellTrustPlatform extends AbstractSMSPlatform {

    /**
     * Interface implementation
     * Send SMS using CellTrust provider
     * @param phoneNumber - mobile phone number
     * @param message - text
     * @return unique message id
     * @throws SMSException
     *
     * @see com.rosettastone.succor.service.SMSPlatform
     */
    public String sendMessage(String phoneNumber, String message,Locale locale) throws SMSException {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("SMSDestination", phoneNumber));
        formparams.add(new BasicNameValuePair("Message", message));
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost httppost = new HttpPost(protocol + "://" + url +"?Username=" + username
                    + "&Password=" + password + "&AllowInsecure=true");
            httppost.setEntity(entity);
            String str = sendRequest(httppost);
            if (str.contains("MessageId")) {
                Pattern pattern = Pattern.compile("<MessageId>(\\w+)</MessageId>");
                Matcher m = pattern.matcher(str);
                if (!m.find()) {
                    throw new SMSException("Invalid service response " + str);
                }
                return m.group(1);
            }
            if (str.contains("ErrorCode")) {
                Pattern pattern = Pattern.compile("<ErrorCode>(\\w+)</ErrorCode>");
                Matcher m = pattern.matcher(str);
                if (!m.find()) {
                    throw new SMSException("Invalid service response " + str);
                }
                String errorCode = m.group(1);

                pattern = Pattern.compile("<ErrorString>(.+)</ErrorString>");
                m = pattern.matcher(str);
                if (!m.find()) {
                    throw new SMSException("Invalid service response " + str);
                }
                String errorString = m.group(1);

                throw new SMSException("ErrorCode: " + errorCode + " ErrorString:" + errorString);
            }
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new SMSException(e);
        }
    }


}
