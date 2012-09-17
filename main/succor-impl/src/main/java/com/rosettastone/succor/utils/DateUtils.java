package com.rosettastone.succor.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * The {@code DateUtils} the functionality for work with date.
 */
public class DateUtils {

    private static final long DAY = 24*60*60*1000;

    /**
     * Calculate date:
     * (date param) subtract number of days
     * Result date has format yyyymmdd as long number
     * subtract 10 days
     *
     * @param date
     * @param days number of days that we should substract
     * @return date as long number
     */
    public static long getDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        long time = normalizeDate(date) - days * DAY;
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year*10000 + month*100 + day;
    }

    /**
     * Returns interval in days between two dates.
     *
     * @param from
     * @param date - second date in long format
     * @return number of days
     */
    public static int getDaysIntervalLength(Date from, long date) {
        int year = (int) (date / 10000);
        date %= 10000;
        int month = (int) (date / 100);
        date %= 100;
        int day = (int) (date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long interval = normalizeDate(from) - calendar.getTimeInMillis();
        int days = (int) (interval / DAY);
        return days;
    }

    /**
     * Normalize date i. e, set hours, minutes, seconds and milliseconds to 0 and save just clear date.
     * @param date
     * @return normalized date in long format.
     */
    private static long normalizeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
