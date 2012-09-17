package com.rosettastone.succor.web.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The {@code Ticket} represents ticket entity from db.
 */
@Entity
@Table(name = "ticket", uniqueConstraints = {@UniqueConstraint(columnNames={"rule_id", "type"})})
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "summary")
    private String summary;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private TicketType type;

    public Ticket() {}

    public Ticket(TicketType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (summary != null ? !summary.equals(ticket.summary) : ticket.summary != null) return false;
        if (type != ticket.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = summary != null ? summary.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
