package com.fyp.eventBackend.EventGeneration.Response;

import java.util.Date;

import com.fyp.eventBackend.Common.BasicHttpResponse;

public class EventDetailResponse extends BasicHttpResponse {

	private Integer id;

	private String name;

	private boolean unlimitedParticipant;

	private int maxAttendee;

	private String dateTime;

	private int currentParticipant;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUnlimitedParticipant() {
		return unlimitedParticipant;
	}

	public void setUnlimitedParticipant(boolean unlimitedParticipant) {
		this.unlimitedParticipant = unlimitedParticipant;
	}

	public int getMaxAttendee() {
		return maxAttendee;
	}

	public void setMaxAttendee(int maxAttendee) {
		this.maxAttendee = maxAttendee;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getCurrentParticipant() {
		return currentParticipant;
	}

	public void setCurrentParticipant(int currentParticipant) {
		this.currentParticipant = currentParticipant;
	}

}
