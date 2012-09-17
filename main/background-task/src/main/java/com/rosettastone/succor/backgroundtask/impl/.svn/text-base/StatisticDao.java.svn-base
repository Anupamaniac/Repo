package com.rosettastone.succor.backgroundtask.impl;

import com.rosettastone.succor.backgroundtask.MessageStatistic;

import java.util.Date;

/**
 * Base interface for global statistic
 * @see StatisticDaoImpl
 */
public interface StatisticDao {

    /**
     * Calculate and return statistic for messages that appear after specified date.
     * @param afterDate
     * @return statistic object
     */
    public MessageStatistic loadMessageStatistic(Date afterDate);

}
