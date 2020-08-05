package com.fyp.eventBackend.Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_generator")
	@SequenceGenerator(name = "ticket_generator", sequenceName = "ticket_seq")
	@Column(name = "id", unique = true)
	private Integer id;

	@Column(unique = true, name = "ticketNo")
	private String ticketNo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "attendee_id")
	private Attendee attendee;

	@Column(name = "attendanceStatus")
	private String attendanceStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_id")
	private Event event;
	
	@Column(unique = true, name = "objectToken")
	private String objectToken;
	
	@Column(unique = true, name = "temperature")
	private Float temperature;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Attendee getAttendee() {
		return attendee;
	}

	public void setAttendee(Attendee attendee) {
		this.attendee = attendee;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public String getObjectToken() {
		return objectToken;
	}

	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	
}
