package com.rosettastone.succor.timertask.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "report_entity")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    @Enumerated(EnumType.STRING)
    private ReportEntityType type;

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

    public ReportEntityType getType() {
        return type;
    }

    public void setType(ReportEntityType type) {
        this.type = type;
    }
}
