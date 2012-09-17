package com.rosettastone.succor.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rosettastone.succor.utils.LocalizedMessageSource;

/**
 * Test implementation of the LocalizedMessageSource.
 * Checks that the message key exists in all resource bundles.
 * 
 * @author Igor Yarkov
 *
 */
public class ResourceBundleLocalizedMessageSource implements LocalizedMessageSource {

    private static final Log LOG = LogFactory.getLog(ResourceBundleLocalizedMessageSource.class);
    private static final String BUNDLE_FILE_PREFIX = "/messages";
    private static final String BUNDLE_FILE_SUFFIX = ".properties";

    private List<ResourceBundle> bundles;
    private Map<ResourceBundle, Locale> bundlesMap;
    private ResourceBundle enBundle;

    public ResourceBundleLocalizedMessageSource() {
        bundles = new ArrayList<ResourceBundle>();
        bundlesMap = new HashMap<ResourceBundle, Locale>();
        initBundle(Locale.ENGLISH);
        initBundle(Locale.JAPANESE);
        initBundle(Locale.KOREAN);
        initBundle(new Locale("ru"));
        enBundle = bundles.get(0);
    }

    private void initBundle(Locale locale) {
        InputStream is = null;
        try {
            String lang;
            if (Locale.ENGLISH.equals(locale)) {
                lang = "";
            } else {
                lang = "_" + locale.getLanguage();
            }
            String path = BUNDLE_FILE_PREFIX + lang + BUNDLE_FILE_SUFFIX;
            is = this.getClass().getResourceAsStream(path);
            ResourceBundle bundle = new PropertyResourceBundle(is);
            bundles.add(bundle);
            bundlesMap.put(bundle, locale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.warn("can not close input stream", e);
                }
            }
        }
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
        for (ResourceBundle bundle : bundles) {
            //Check that message code exists in all bundles
            crerateMessage(code, bundle, arguments);
        }
        return crerateMessage(code, enBundle, arguments);
    }


    private String crerateMessage(String code, ResourceBundle bundle, String[] arguments) {
        try {
            String messageTemplate = bundle.getString(code);
            return MessageFormat.format(messageTemplate, (Object[]) arguments);
        } catch (RuntimeException e) {
            LOG.error("Can not generate message for bundle: " + bundlesMap.get(bundle));
            throw e;
        }
    }

}
