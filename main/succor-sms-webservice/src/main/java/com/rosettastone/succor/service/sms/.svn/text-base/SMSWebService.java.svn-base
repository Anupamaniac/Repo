package com.rosettastone.succor.service.sms;

import com.rosettastone.succor.exception.SMSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * User: Nikolay Sazonov
 * Date: 5/10/11
 * Webservice for sending SMS.
 */
@Controller
@RequestMapping(value = "/sms")
public class SMSWebService {

    private static final Log LOG = LogFactory.getLog(SMSWebService.class);

    @Autowired
    private SMSSender smsSender;


    private Validator validator;

    @Autowired
    public SMSWebService(Validator validator) {
        this.validator = validator;
    }


    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> sendMessage(@RequestBody SMSRequestVO requestVO,
                                              HttpServletResponse response) {
        Set<ConstraintViolation<SMSRequestVO>> failures = validator.validate(requestVO);
        if (!failures.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return validationMessages(failures);
        } else {
            Locale locale = new Locale(requestVO.getLocale());
            try {
                String messageId = smsSender.sendMessage(requestVO.getPhone(), requestVO.getText(), locale);
                return Collections.singletonMap("messageId", messageId);
            } catch (SMSException e) {
                LOG.error(e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                Map<String, String> failureMessages = new HashMap<String, String>();
                failureMessages.put("error", e.getMessage());
                return failureMessages;
            }
        }
    }

    /**
     * Validate objects and put errors to the Map
     * @param failures
     * @return
     */
    private Map<String, String> validationMessages(Set<ConstraintViolation<SMSRequestVO>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<SMSRequestVO> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
        }
        return failureMessages;
    }


}
