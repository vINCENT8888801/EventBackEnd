package com.fyp.eventBackend.Database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Attendee {

	@Id
	@Column(name = "id", unique = true)
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attendee")
	private List<Ticket> tickets = new ArrayList<Ticket>();

	@Column(name = "image64bit", nullable = false)
	private String image64bit;

	@OneToOne
    @MapsId
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getImage64bit() {
		return image64bit;
	}

	public void setImage64bit(String image64bit) {
		this.image64bit = image64bit;
	}

}
