package com.rosettastone.succor.web.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * The {@code Event} represents event entity from db.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Set<Value> values;

    @ManyToOne
    @JoinColumn(name = "message_type_id")
    private EventMessageType messageType;

    public Event() {
        super();
    }

    public Event(String name) {
        this.name = name;
    }

//    public Event(String name, String className) {
//        this.name = name;
//        this.className = className;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Value> getValues() {
        return values;
    }

    public void setValues(Set<Value> values) {
        this.values = values;
    }

    public EventMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(EventMessageType messageType) {
        this.messageType = messageType;
    }
}