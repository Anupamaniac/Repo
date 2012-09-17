package com.rosettastone.succor.timertask.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "email_report_history")
public class EmailReportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
