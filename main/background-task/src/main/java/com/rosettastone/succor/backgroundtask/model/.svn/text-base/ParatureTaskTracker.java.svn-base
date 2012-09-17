package com.rosettastone.succor.backgroundtask.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * The model object for history about task execution.
 * Each task execution produces one HistoryEntity object.
 *
 * It is mapped to DB table.
 */

@Entity
@Table(name = "parature_task_tracker")
public class ParatureTaskTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name="Track_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParatureAction paratureAction;

    @Enumerated(EnumType.STRING)
    private ParatureActionStatus status;
    
    @Column(name = "retryCount", nullable = false, columnDefinition = "int default 0")
    private Integer retryCount = 0;
    
    
    /*@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "task_id", updatable = false, insertable = false)*/
//    @JoinColumn(name = "task_id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;
    
    @OneToMany(cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
    @JoinColumn(name = "tracker_id")
    @OrderBy("id")
    private Set<ParatureTaskHistory> paratureTaskHistory = new HashSet<ParatureTaskHistory>();
    
    private String ticketId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setParatureAction(ParatureAction paratureAction) {
		this.paratureAction = paratureAction;
	}

	public ParatureAction getParatureAction() {
		return paratureAction;
	}

	public void setStatus(ParatureActionStatus status) {
		this.status = status;
	}

	public ParatureActionStatus getStatus() {
		return status;
	}

	public void setParatureTaskHistory(Set<ParatureTaskHistory> paratureTaskHistory) {
		this.paratureTaskHistory = paratureTaskHistory;
	}

	public Set<ParatureTaskHistory> getParatureTaskHistory() {
		return paratureTaskHistory;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketId() {
		return ticketId;
	}

	
}
