package com.fyp.eventBackend.Ticket.Response;

import com.fyp.eventBackend.Common.BasicHttpResponse;
import com.fyp.eventBackend.Database.Event;

public class RegisterTicketResponse extends BasicHttpResponse{
	
	private Event eventRegistered;

	public Event getEventRegistered() {
		return eventRegistered;
	}

	public void setEventRegistered(Event eventRegistered) {
		this.eventRegistered = eventRegistered;
	}
	
}
