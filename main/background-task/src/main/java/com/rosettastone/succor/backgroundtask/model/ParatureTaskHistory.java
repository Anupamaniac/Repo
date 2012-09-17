package com.rosettastone.succor.backgroundtask.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rosettastone.succor.exception.ParatureErrorCode;

/**
 * The model object for history about task execution.
 * Each task execution produces one HistoryEntity object.
 *
 * It is mapped to DB table.
 */

@Entity
@Table(name = "parature_task_history")
public class ParatureTaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name="Track_id")
    private Long id;

    @Lob
    private String request;
    
    @Column(name = "created_at")
    private Date requestSentAt;
    
    @Lob
    private String response; 
    
    @Column(name = "recieved_at")
    private Date responseRcvdAt;
    
    @Enumerated(EnumType.STRING)
    private ParatureErrorCode paratureErrorCode;

    @Lob
    private String paratureException;
   
    /*@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "task_id", updatable = false, insertable = false)*/
//    @JoinColumn(name = "task_id")
    /*@ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;*/

    @ManyToOne(optional = false)
    @JoinColumn(name = "tracker_id")
    private ParatureTaskTracker paratureTaskTracker;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public void setParatureErrorCode(ParatureErrorCode paratureErrorCode) {
		this.paratureErrorCode = paratureErrorCode;
	}

	public ParatureErrorCode getParatureErrorCode() {
		return paratureErrorCode;
	}

	public void setRequestSentAt(Date requestSentAt) {
		this.requestSentAt = requestSentAt;
	}

	public Date getRequestSentAt() {
		return requestSentAt;
	}

	public void setResponseRcvdAt(Date responseRcvdAt) {
		this.responseRcvdAt = responseRcvdAt;
	}

	public Date getResponseRcvdAt() {
		return responseRcvdAt;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequest() {
		return request;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setParatureTaskTracker(ParatureTaskTracker paratureTaskTracker) {
		this.paratureTaskTracker = paratureTaskTracker;
	}

	public ParatureTaskTracker getParatureTaskTracker() {
		return paratureTaskTracker;
	}

	public void setParatureException(String paratureException) {
		this.paratureException = paratureException;
	}

	public String getParatureException() {
		return paratureException;
	}

	
}
