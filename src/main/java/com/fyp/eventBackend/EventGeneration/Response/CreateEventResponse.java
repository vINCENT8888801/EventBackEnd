package com.fyp.eventBackend.EventGeneration.Response;

import com.fyp.eventBackend.Common.BasicHttpResponse;
import com.fyp.eventBackend.Database.Event;

public class CreateEventResponse extends BasicHttpResponse{
	
	private Event newEvent;

	public Event getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Event newEvent) {
		this.newEvent = newEvent;
	}

}
