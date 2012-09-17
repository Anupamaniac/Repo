package com.rosettastone.succor.timertask.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class StatisticBean {

    private Date startDate;
    private Date endDate;

    private Map<ReportEntityType, Long> statistic = new HashMap<ReportEntityType, Long>();

    public void put(ReportEntityType type, Long counter) {
        statistic.put(type, counter);
    }

    public long get(ReportEntityType type) {
        Long value = statistic.get(type);
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    public long getTicketTotal() {
        long total = 0;
        total += get(ReportEntityType.PHONE_TICKET);
        total += get(ReportEntityType.POSTAL_TICKET);
        total += get(ReportEntityType.INSTITUTIONAL_TICKET);
        total += get(ReportEntityType.SUPER_TIKET);
        return total;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
