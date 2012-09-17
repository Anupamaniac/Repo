package com.rosettastone.succor.service.sms;

import com.rosettastone.succor.exception.SMSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Required;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * User: Nikolay Sazonov
 * Date: 5/3/11
 * Implementation of SMS API specified for Clickatell mobile provider.
 */
public class ClickatellPlatform extends AbstractSMSPlatform {
	
	private static final Log LOG = LogFactory.getLog(ClickatellPlatform.class);

    private String apiId;

    /**
     * Interface implementation
     * Send SMS using Clickatell provider
     * @param phoneNumber - mobile phone number
     * @param message - text
     * @return unique message id
     * @throws SMSException
     *
     * @see com.rosettastone.succor.service.SMSPlatform
     */
    public String sendMessage(String phoneNumber, String message,Locale locale) throws SMSException {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("to", phoneNumber));
        LOG.debug("phoneNumber= "+phoneNumber+"\nmessage= "+message+"\nlocale= "+locale);
        LOG.debug("\nmessage length = "+message.length());
        if(locale.equals(Locale.KOREAN)){
        	int strlen = message.length();
        	if(strlen > 35){// clickatell supports only a max of 35 characters
        		LOG.debug("korean message is greater than 35 characters");
        		strlen = 35;
        	}
            char[] chars = new char[strlen];
            LOG.debug("added a space for korean message");
            message.getChars(0, strlen, chars, 0);
            formparams.add(new BasicNameValuePair("text", StringConverter.getUnicodedString(chars)));
            formparams.add(new BasicNameValuePair("unicode","1"));
            LOG.debug("messsage sent is"+message+"to"+phoneNumber);
            LOG.debug("messsage sent is korean");
        }else{
        	 LOG.debug("messsage sent is"+message+"to"+phoneNumber);
        	formparams.add(new BasicNameValuePair("text", message));
        }
        
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost httppost = new HttpPost(protocol + "://" + url +"?api_id="
                    + apiId + "&user=" + username + "&password=" + password );
            httppost.setEntity(entity);
            String str = sendRequest(httppost);
            if (str.contains("ERR")) {
                throw new SMSException("Error sending SMS " + str);
            }
            if (!str.contains("ID:")) {
                throw new SMSException("Invalid SMS service response " + str);
            }
            return str.replace("ID:", "").trim();

        } catch (UnsupportedEncodingException e) {
            throw new SMSException(e);
        }
    }

    @Required
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }


}
