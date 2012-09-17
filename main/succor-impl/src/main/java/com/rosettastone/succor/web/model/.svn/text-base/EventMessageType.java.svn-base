package com.rosettastone.succor.web.model;

import javax.persistence.*;

/**
 * User: mixim
 * Date: 27.09.11
*/
@Entity
@Table(name = "message_type")
public class EventMessageType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "message_type")
    private String messageType;

    @Column(name = "class_name")
    private String className;

    @Column(name = "priority")
    private short priority;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String type) {
        this.messageType = type;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}