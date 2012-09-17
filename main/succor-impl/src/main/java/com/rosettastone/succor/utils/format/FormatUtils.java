package com.rosettastone.succor.utils.format;

import java.text.Format;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.FastDateFormat;

import com.rosettastone.succor.model.bandit.Customer;

/**
 * The {@code FormatUtils} allows to format data (address of customer): {@code csvformatCustomerAddress},
 * allows to create particular comment {@code createComment}.
 */
public final class FormatUtils {

    private static final Format DATE_FORMAT = FastDateFormat.getInstance("MM/dd/yy hh:mm aa");

    private FormatUtils() {
    }

    /*
     * Formats {@code customer} with "\n" {@code separator}.
     * Build details field. [ST_ADDRESS1] [ST_ADDRESS2] [CITY], [STATE_CODE] [POSTAL_CODE] [COUNTRY_NAME]
     *
     * @param customer
     * @return string
     */
    public static String formatCustomerAddress(Customer customer) {
        return formatCustomerAddress(customer, "\n");
    }

    /**
     * Formats {@code customer} with "," {@code separator}.
     *
     * @param customer
     * @return string
     */
    public static String csvformatCustomerAddress(Customer customer) {
        return formatCustomerAddress(customer, ", ");
    }

    /**
     * Creates a string from {@code customer}'s address with using of {@code separator}.
     *
     * @param customer
     * @param separator
     * @return string
     */
    private static String formatCustomerAddress(Customer customer, String separator) {
        if ((customer.getAddressLine1() == null) || (customer.getCity() == null) || customer.getCountryIso() == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(customer.getAddressLine1()).append(separator);
        if (customer.getAddressLine2() != null) {
            builder.append(customer.getAddressLine2()).append(separator);
        }
        builder.append(customer.getCity()).append(", ");
        boolean hasPostalOrState = false;
        if (customer.getStateProvince() != null) {
            builder.append(customer.getStateProvince()).append(" ");
            hasPostalOrState = true;
        }
        if (customer.getPostalCode() != null) {
            builder.append(customer.getPostalCode());
            hasPostalOrState = true;
        }
        if (hasPostalOrState) {
            builder.append(separator);
        }
        builder.append(customer.getCountryIso());
        return builder.toString();
    }

    /**
     *
     * Creates a comment string with formatted date.
     * @param date
     * @return string
     */
    public static String createComment(Date date) {
        return "This trigger was generated on " + DATE_FORMAT.format(date) + " EST.";
    }

    /**
     * Creates a comment string with formatted date and email if <tt>(email != null)</tt>.
     * @param date
     * @param email
     * @return string
     */
    public static String createComment(Date date, String email) {
        if (email == null) {
            return createComment(date);
        }
        return createComment(date) + '\n' + email;
    }

    /**
     * Removes html data from {@code input}.
     *
     * @param input
     * @return string
     */
    public static String stripHTML(String input) {
        return input.replaceAll("\\<.*?>", "").replaceAll("\\<style>.*?</style>", "").trim();
    }

    /**
     * Creates a string from parameters.
     *
     * @param productName
     * @param language
     * @param date
     * @return string
     */
    public static String productLanguageLevelTime(String productName, String language, Date date) {
        return language + " " + productName + '\n' + DATE_FORMAT.format(date);
    }

    /**
     * Create a string from {@code level}, {@code unit}.
     *
     * @param level
     * @param unit
     * @return string
     */
    public static String formatLevelUnit(Integer level, Integer unit) {
        return String.format("Level %s Unit %s", level, unit);
    }

    /**
     * Checks the validity of the {@code phone}.
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneValid(String phone) {
        if (phone == null) {
            return false;
        }

        if ("".equals(phone.trim())) {
            return false;
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
