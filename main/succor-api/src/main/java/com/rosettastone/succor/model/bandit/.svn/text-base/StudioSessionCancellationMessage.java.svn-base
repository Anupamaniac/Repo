package com.rosettastone.succor.model.bandit;

import java.util.Date;

public class StudioSessionCancellationMessage extends MessageLevelUnitStartTime {
	
    
    private SessionCancelReason reason;
    private Boolean cancelled;
    private Boolean teacherTechnicalProblem;
    private Boolean learnerTechnicalProblem;
    private Date teacherFirstSeenAt;
    private Date learnerFirstSeenAt;
    private String cancelledBy;
    private Date cancelledAt;
    private Integer attendanceId;
    private Integer numberOfSeats;
    private Boolean solo = Boolean.FALSE;
    
    @JsonField
    public SessionCancelReason getReason() {
        return reason;
    }
    public void setReason(SessionCancelReason reason) {
        this.reason = reason;
    }
    @JsonField
    public Boolean getCancelled() {
        return cancelled;
    }
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }
    @JsonField("teacher_technical_problem")
    public Boolean getTeacherTechnicalProblem() {
        return teacherTechnicalProblem;
    }
    public void setTeacherTechnicalProblem(Boolean teacherTechnicalProblem) {
        this.teacherTechnicalProblem = teacherTechnicalProblem;
    }
    @JsonField("learner_technical_problem")
    public Boolean getLearnerTechnicalProblem() {
        return learnerTechnicalProblem;
    }
    public void setLearnerTechnicalProblem(Boolean learnerTechnicalProblem) {
        this.learnerTechnicalProblem = learnerTechnicalProblem;
    }
    @JsonField("teacher_first_seen_at")
    public Date getTeacherFirstSeenAt() {
        return teacherFirstSeenAt;
    }
    public void setTeacherFirstSeenAt(Date teacherFirstSeenAt) {
        this.teacherFirstSeenAt = teacherFirstSeenAt;
    }
    @JsonField("learner_first_seen_at")
    public Date getLearnerFirstSeenAt() {
        return learnerFirstSeenAt;
    }
    public void setLearnerFirstSeenAt(Date learnerFirstSeenAt) {
        this.learnerFirstSeenAt = learnerFirstSeenAt;
    }
    @JsonField("cancelled_by")
    public String getCancelledBy() {
        return cancelledBy;
    }
    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
    @JsonField("cancelled_at")
    public Date getCancelledAt() {
        return cancelledAt;
    }
    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }
    @JsonField("attendance_id")
    public Integer getAttendanceId() {
        return attendanceId;
    }
    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }
	public void setSolo(Boolean solo) {
		this.solo = solo;
	}
	
	public Boolean getSolo() {
		return solo;
	}
	
	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	@JsonField("number_of_seats")
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}
   
}
