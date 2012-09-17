package com.rosettastone.succor.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * The {@code LocalizedMessageSourceImpl} allows to get
 * message source for specified locale.
 */
public class LocalizedMessageSourceImpl implements LocalizedMessageSource {

    private MessageSource messageSource;

    private Locale locale;

    public LocalizedMessageSourceImpl(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getMessage(String code) {
        return getMessage(code, new String[]{});
    }

    @Override
    public String getMessage(String code, String argument) {
        return getMessage(code, new String[]{argument});
    }

    @Override
    public String getMessage(String code, String argument1, String argument2) {
       return getMessage(code, new String[]{argument1, argument2});
    }

    @Override
    public String getMessage(String code, String[] arguments) {
        return messageSource.getMessage(code, arguments, locale);
    }
}
