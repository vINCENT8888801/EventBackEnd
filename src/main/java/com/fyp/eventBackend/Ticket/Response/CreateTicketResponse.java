package com.fyp.eventBackend.Ticket.Response;

import com.fyp.eventBackend.Common.BasicHttpResponse;
import com.fyp.eventBackend.Database.Ticket;

public class CreateTicketResponse extends BasicHttpResponse{
	
	private int eventId;
	
	private Ticket ticket;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	
	
}
