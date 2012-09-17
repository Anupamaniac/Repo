package com.rosettastone.succor.service.sms;

import com.rosettastone.succor.exception.SMSException;
import com.rosettastone.succor.service.SMSPlatform;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Locale;

/**
 * User: Nikolay Sazonov
 * Date: 5/4/11
 * Universal sender, that support 2 mobile providers: CellTrust, Clickatell
 */
public class SMSSender {

    private static final Log LOG = LogFactory.getLog(SMSSender.class);

    private SMSPlatform clickatell;

    private SMSPlatform cellTrust;

    public void setClickatell(SMSPlatform clickatell) {
        this.clickatell = clickatell;
    }

    public void setCellTrust(SMSPlatform cellTrust) {
        this.cellTrust = cellTrust;
    }

    /**
     * Send message to user
     * @param phoneNumber - mobile phone number
     * @param message - sms message
     * @param locale - user locale
     * @return
     * @throws SMSException
     */
    public String sendMessage(String phoneNumber, String message, Locale locale) throws SMSException {
        if (StringUtils.isBlank(phoneNumber)) {
            throw new SMSException("phone number is empty");
        }
        if (StringUtils.isBlank(message)) {
            throw new SMSException("message is empty");
        }
        if (locale == null) {
            throw new SMSException("locale can't be null");
        }

        String messageId = null;
        if (locale.equals(Locale.US)) {
            // for US try to use celltrust first
            try {
                messageId = cellTrust.sendMessage(phoneNumber, message,locale);
                LOG.debug("Message has been sent with CellTrust,messageId:" + messageId);
                return messageId;
            } catch (SMSException e) {
                LOG.error(e);
                messageId = clickatell.sendMessage(phoneNumber, message,locale);
                LOG.debug("Message has been sent with Clickatell,messageId:" + messageId);
                return messageId;
            }
        } else {
            // for other users use clickatell
            messageId = clickatell.sendMessage(phoneNumber, message,locale);
            LOG.debug("Message has been sent with Clickatell,messageId:" + messageId);
            return messageId;
        }
    }
}
