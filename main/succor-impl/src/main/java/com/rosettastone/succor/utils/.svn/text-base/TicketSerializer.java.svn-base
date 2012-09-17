package com.rosettastone.succor.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.OutreachTicket;
import com.rosettastone.succor.model.parature.SuperTicket;
import com.rosettastone.succor.model.parature.Ticket;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.model.parature.TicketOriginType;
import com.rosettastone.succor.model.parature.TicketType;
import com.rosettastone.succor.parature.ParatureProperties;
import com.rosettastone.succor.utils.converter.CustomFieldConverter;
import com.rosettastone.succor.utils.converter.CustomerConverter;
import com.rosettastone.succor.utils.converter.Names;
import com.rosettastone.succor.utils.converter.StringFieldConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * The {@code TicketSerializer} allows to serialize tickets.
 */

public class TicketSerializer implements Names {

    private static final Log LOG = LogFactory.getLog(TicketSerializer.class);
    private static final int INITIAL_CAPACITY = 8192;

    private XStream xstream;
    private ParatureProperties paratureProperties;

    /**
     * Makes some actions before serialization.
     */
    public void init() {
        LOG.info("Initialize xstream");
        xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
        registerConverters();
        registerAliases();

        xstream.useAttributeFor(Ticket.class, ID);
        LOG.info("Initialize xstream - finished");
    }

    /**
     * Creates inputStream from serialized ticket.
     *
     * @param ticket
     * @return inputStream
     */
    public InputStream serialize(Ticket ticket) {
        ByteArrayOutputStream output = new ByteArrayOutputStream(INITIAL_CAPACITY);
        xstream.toXML(ticket, output);
        InputStream inputStream = new ByteArrayInputStream(output.toByteArray());
        return inputStream;
    }

    /**
     * Registers aliases for xstream.
     */
    private void registerAliases() {
        LOG.info("Register aliases");
        xstream.alias(TicketSerializer.TICKET, SuperTicket.class);
        xstream.alias(TicketSerializer.TICKET, OutreachTicket.class);

        xstream.aliasField(TicketSerializer.TICKET_CUSTOMER, Ticket.class, "customer");
        xstream.aliasField(CUSTOM_FIELD, Ticket.class, "ticketType");
        xstream.aliasField(EMAIL_NOTIFICATION, Ticket.class, "emailNotification");
        xstream.aliasField(HIDE_FROM_CUSTOMER, Ticket.class, "hideFromCustomer");

        xstream.aliasField(CUSTOM_FIELD, SuperTicket.class, "language");
        xstream.aliasField(CUSTOM_FIELD, SuperTicket.class, "summary");
        xstream.aliasField(CUSTOM_FIELD, SuperTicket.class, "details");
        xstream.aliasField(CUSTOM_FIELD, SuperTicket.class, "originType");

        xstream.aliasField(CUSTOM_FIELD, OutreachTicket.class, "contactReason");
        xstream.aliasField(CUSTOM_FIELD, OutreachTicket.class, "originType");
        xstream.aliasField(CUSTOM_FIELD, OutreachTicket.class, "internalDetails");
        xstream.aliasField(CUSTOM_FIELD, OutreachTicket.class, "summary");
    }

    /**
     * Registers converters for xstream.
     */
    private void registerConverters() {
        LOG.info("Register converters");
        xstream.registerConverter(new CustomFieldConverter(TicketLanguageType.class, "parature.field.language",
                paratureProperties));
        xstream.registerConverter(new CustomFieldConverter(TicketOriginType.class, "parature.field.ticketOrigin",
                paratureProperties));
        xstream.registerConverter(new CustomFieldConverter(ContactReasonType.class, "parature.field.contactReason",
                paratureProperties));
        xstream.registerConverter(new CustomFieldConverter(TicketType.class, "parature.field.type", paratureProperties));

        xstream.registerConverter(new CustomerConverter());
        xstream.registerLocalConverter(SuperTicket.class, "details", new StringFieldConverter("parature.field.details",
                paratureProperties));
        xstream.registerLocalConverter(SuperTicket.class, "summary", new StringFieldConverter("parature.field.summary",
                paratureProperties));
        xstream.registerLocalConverter(OutreachTicket.class, "summary", new StringFieldConverter(
                "parature.field.summary", paratureProperties));
        xstream.registerLocalConverter(OutreachTicket.class, "internalDetails", new StringFieldConverter(
                "parature.field.internalDetails", paratureProperties));
    }

    public void setParatureProperties(ParatureProperties paratureProperties) {
        this.paratureProperties = paratureProperties;
    }
}
