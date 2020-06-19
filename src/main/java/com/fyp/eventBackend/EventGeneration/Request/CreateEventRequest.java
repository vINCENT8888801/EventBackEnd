package com.fyp.eventBackend.EventGeneration.Request;


public class CreateEventRequest {
	
	private String name;

	private String time;
	
	private int maxAttendee;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getMaxAttendee() {
		return maxAttendee;
	}

	public void setMaxAttendee(int maxAttendee) {
		this.maxAttendee = maxAttendee;
	}
	
}
