package com.rosettastone.succor.backgroundtask.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * The model object for message. It is mapped to DB table.
 */
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String message;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "message")
    @OrderBy("id")
    private Set<Task> tasks;

    @Column(name = "message_type")
    private String type;

    public Long getId() {
        return id; 
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
