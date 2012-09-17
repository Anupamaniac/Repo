package com.rosettastone.succor.utils;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * The{@TicketXMLParser} allows to parse ticket from XML.
 */
public final class TicketXMLParser {

    private static final Log LOG = LogFactory.getLog(TicketXMLParser.class);

    private static final String ID = "id";

    private TicketXMLParser() {
    }

    /**
     *
     * Creates documentBuilder.
     *
     * @return documentBuilder
     * @throws ParserConfigurationException
     */
    public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Gets ticket id from XML {@code response}.
     *
     * @param response
     * @return string
     */
    public static String getTicketIdFromXML(String response) {
        String ticketId = null;
        Document doc;
        try {
            doc = getDocumentBuilder().parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
            ticketId = doc.getElementsByTagName("Ticket").item(0).getAttributes().getNamedItem(ID).getNodeValue()
                    .toString();
        } catch (Exception e) {
            // TODO What should we do in case of exception? What invokers will do with NULL value?
            LOG.error("Can not get Ticket Id from XML document " + response);
        }
        return ticketId;
    }

    /**
     * Gets value for {@code optionId} from {@code response}.
     *
     * @param response
     * @param optionId
     * @return string
     */
    public static String getOptionTextValue(String response, String optionId) {
        Document doc;
        try {
            doc = getDocumentBuilder().parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
            NodeList options = doc.getElementsByTagName("Option");
            for (int i = 0; i < options.getLength(); i++) {
                if (options.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(optionId)) {
                    return options.item(i).getChildNodes().item(0).getFirstChild().getNodeValue();
                }
            }
        } catch (Exception e) {
            LOG.debug("Unxpected error", e);
        }
        return null;
    }

}
