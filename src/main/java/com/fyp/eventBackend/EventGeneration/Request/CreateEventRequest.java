package com.fyp.eventBackend.EventGeneration.Request;

public class CreateEventRequest {

	private String name;

	private String dateTime;

	private boolean unlimitedParticipant;

	private int maxAttendee;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getMaxAttendee() {
		return maxAttendee;
	}

	public void setMaxAttendee(int maxAttendee) {
		this.maxAttendee = maxAttendee;
	}

	public boolean isUnlimitedParticipant() {
		return unlimitedParticipant;
	}

	public void setUnlimitedParticipant(boolean unlimitedParticipant) {
		this.unlimitedParticipant = unlimitedParticipant;
	}

}
