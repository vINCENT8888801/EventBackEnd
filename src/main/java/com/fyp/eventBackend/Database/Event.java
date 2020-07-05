package com.fyp.eventBackend.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // This tells Hibernate to make a table out of this class
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
	@SequenceGenerator(name="event_generator", sequenceName = "event_seq")
	@Column(name="id", unique=true)
	private Integer id;
	
	@Column(name="name",nullable = false)
	private String name;
	
	@Column(name="unlimitedParticipant",nullable = false)
	private boolean unlimitedParticipant;
	
	@Column(name="maxAttendee",nullable = true)
	private int maxAttendee;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datetime",nullable = false)
	private Date datetime;
	
	
	@JsonIgnore
	@OneToMany (fetch = FetchType.LAZY, mappedBy = "event")
    private List<Ticket> tickets = new ArrayList<Ticket>(); 

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

	public Date getDateTime() {
		return datetime;
	}

	public void setDateTime(Date time) {
		this.datetime = time;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
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
