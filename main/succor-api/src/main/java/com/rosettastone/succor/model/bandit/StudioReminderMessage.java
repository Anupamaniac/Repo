package com.rosettastone.succor.model.bandit;


public class StudioReminderMessage extends MessageLevelUnitStartTime {
	
    private Integer hours;
    private Integer sessionId;
    private Integer studentId;
    private Integer attendanceId;
    private Integer numberOfSeats;
    private Long cancellationWindowInSeconds;
    private Boolean solo = Boolean.FALSE;

    @JsonField
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @JsonField("eschool_session_id")
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @JsonField("student_id")
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

	public void setCancellationWindowInSeconds(
			Long cancellationWindowInSeconds) {
		this.cancellationWindowInSeconds = cancellationWindowInSeconds;
	}

	@JsonField("cancellation_window_in_seconds")
	public Long getCancellationWindowInSeconds() {
		return cancellationWindowInSeconds;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	@JsonField("number_of_seats")
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}
}
