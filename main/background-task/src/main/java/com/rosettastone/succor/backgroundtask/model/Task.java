package com.rosettastone.succor.backgroundtask.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * The model object for task. It is mapped to DB table.
 */
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "bean_name")
    private String beanName;

    @Column(name = "method_name")
    private String methodName;

    @Lob
    private String arguments;

    @Transient
    private Object[] rawArguments;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "next_run")
    private Date nextRun;

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "task_id")
    @OrderBy("id")
    private Set<HistoryEntity> history;

    @ManyToOne(optional = false)
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToMany(mappedBy = "task")
    private Set<ParatureTaskTracker> paratureTaskTracker;

    private short priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public Date getNextRun() {
        return nextRun;
    }

    public void setNextRun(Date nextRun) {
        this.nextRun = nextRun;
    }

    public Set<HistoryEntity> getHistory() {
        return history;
    }

    public void setHistory(Set<HistoryEntity> history) {
        this.history = history;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Object[] getRawArguments() {
        return rawArguments;
    }

    public void setRawArguments(Object[] rawArguments) {
        this.rawArguments = rawArguments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public void setParatureTaskTracker(Set<ParatureTaskTracker> paratureTaskTracker) {
        this.paratureTaskTracker = paratureTaskTracker;
    }

    public Set<ParatureTaskTracker> getParatureTaskTracker() {
        return paratureTaskTracker;
    }

    public void addParatureTaskTracker(ParatureTaskTracker p) {
        paratureTaskTracker.add(p);
        p.setTask(this);
    }
}
