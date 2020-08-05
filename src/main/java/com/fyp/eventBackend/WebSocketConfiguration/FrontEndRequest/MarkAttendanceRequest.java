package com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest;

public class MarkAttendanceRequest {
	
	private int ticketId;
	
	private float temperature;

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	

}
