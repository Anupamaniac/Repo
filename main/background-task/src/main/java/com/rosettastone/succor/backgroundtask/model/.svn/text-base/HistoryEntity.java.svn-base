package com.rosettastone.succor.backgroundtask.model;

import java.util.Date;

import javax.persistence.*;

/**
 * The model object for history about task execution.
 * Each task execution produces one HistoryEntity object.
 *
 * It is mapped to DB table.
 */

@Entity
@Table(name = "history_entity")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    @Enumerated(EnumType.STRING)
    private TaskExecutionStatus status;

    @Lob
    private String exception;

    @Lob
    private String stacktrace;

    @ManyToOne
    private Task task;

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

    public TaskExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(TaskExecutionStatus status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
