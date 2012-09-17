package com.rosettastone.succor.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;

@Test
public class DateUtilsTest {

    @Test
    public void getDateTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 5, 7, 3, 55, 59);

        Assert.assertEquals(DateUtils.getDate(calendar.getTime(), 5), 20110602);
        Assert.assertEquals(DateUtils.getDate(calendar.getTime(), 10), 20110528);

        calendar.set(2010, 0, 1, 23, 59, 59);
        Assert.assertEquals(DateUtils.getDate(calendar.getTime(), 1), 20091231);

    }

    @Test
    public void getDaysIntervalLengthTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 5, 7, 3, 55, 59);
        Assert.assertEquals(DateUtils.getDaysIntervalLength(calendar.getTime(), 20110602), 5);
        Assert.assertEquals(DateUtils.getDaysIntervalLength(calendar.getTime(), 20110528), 10);

        calendar.set(2010, 0, 1, 23, 59, 59);
        Assert.assertEquals(DateUtils.getDaysIntervalLength(calendar.getTime(), 20091231), 1);
    }

}
