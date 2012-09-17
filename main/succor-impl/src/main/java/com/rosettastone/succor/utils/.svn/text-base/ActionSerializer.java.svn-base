package com.rosettastone.succor.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import com.rosettastone.succor.model.parature.ActionCsr;
import com.rosettastone.succor.utils.converter.ActionCsrConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rosettastone.succor.model.parature.Action;
import com.rosettastone.succor.utils.converter.ActionConverter;
import com.rosettastone.succor.utils.converter.Names;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * The {@code ActionSerializer} allows to serialize action.
 */
public class ActionSerializer implements Names {

    private static final Log LOG = LogFactory.getLog(ActionSerializer.class);
    private static final int INITIAL_CAPACITY = 8192;

    private XStream xstream;

    @PostConstruct
    public void init() {
        LOG.info("Initialize xstream");
        xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
        registerConverters();
        registerAliases();
        LOG.info("Initialize xstream - finished");
    }

    public InputStream serialize(Action ticketAction) {
        ByteArrayOutputStream output = new ByteArrayOutputStream(INITIAL_CAPACITY);
        xstream.toXML(ticketAction, output);
        InputStream inputStream = new ByteArrayInputStream(output.toByteArray());
        return inputStream;
    }

    public InputStream serializeCsr(ActionCsr ticketAction) {
        ByteArrayOutputStream output = new ByteArrayOutputStream(INITIAL_CAPACITY);
        xstream.toXML(ticketAction, output);
        InputStream inputStream = new ByteArrayInputStream(output.toByteArray());
        return inputStream;
    }

    private void registerAliases() {
        LOG.info("Register aliases");
        xstream.alias(TICKET, Action.class);
        xstream.alias(TICKET, ActionCsr.class);
    }

    private void registerConverters() {
        LOG.info("Register converters");
        xstream.registerConverter(new ActionConverter());
        xstream.registerConverter(new ActionCsrConverter());
    }

}
