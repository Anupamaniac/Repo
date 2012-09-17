package com.rosettastone.succor.timertask;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.util.Collection;

import org.apache.commons.lang.time.FastDateFormat;

import au.com.bytecode.opencsv.CSVWriter;

import com.rosettastone.succor.timertask.model.PostalHistory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convert list of postal history data to CSV format and write to output stream.
 */
public final class CSVConverter {

    private static final Format DATE_FORMAT = FastDateFormat.getInstance("MM/dd/yy hh:mm aa");
    private static final int ARRAY_SIZE = 8;
    private static final Log LOG = LogFactory.getLog(CSVConverter.class);


    private CSVConverter() {
    }

    /**
     * Return <tt>false</tt> can not covert specified collection
     * @param postalHistory collection to be converted
     * @param outputStream stream for store result data
     * @return <tt>true</tt> if converting was successful
     * @throws IOException
     */
    public static boolean convertWithReturn(Collection<PostalHistory> postalHistory, OutputStream outputStream) throws IOException {
        PostalHistory current = new PostalHistory();
        CSVWriter writer = null;
        try {
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, "UTF8");
            writer = new CSVWriter(outputWriter, ',');
            String[] line = new String[ARRAY_SIZE];
            int idx = 0;
            // add csv header
            line[idx++] = "Date/Time of Trigger";
            line[idx++] = "Customer Full Name";
            line[idx++] = "Customer Email";
            line[idx++] = "Customer Shipping Address";
            line[idx++] = "Contact Reason";
            line[idx++] = "Product Level Purchased";
            line[idx++] = "Product Language";
            line[idx++] = "License GUID";
            writer.writeNext(line);

            for (PostalHistory history : postalHistory) {
                current = history;
                idx = 0;
                line[idx++] = DATE_FORMAT.format(history.getCreatedAt());
                line[idx++] = history.getCustomerName();
                line[idx++] = history.getEmail();
                line[idx++] = history.getShippingAddress();
                line[idx++] = enum2string(history.getContactReason());
                line[idx++] = history.getProductLevel();
                line[idx++] = enum2string(history.getLanguage());
                line[idx++] = history.getGuid();
                writer.writeNext(line);
            }
            writer.close();
        } catch (IOException e) {
            LOG.error("IOException while convert PostalHistory: " + current , e);
            throw e;
        } catch (Exception e) {
            LOG.error("Exception while convert PostalHistory: " + current , e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String enum2string(Enum<?> e) {
        if (e == null) {
            return "";
        } else {
            return e.toString();
        }
    }
}
