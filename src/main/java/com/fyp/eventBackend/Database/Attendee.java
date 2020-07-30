package com.fyp.eventBackend.Database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Attendee {

	@Id
	@Column(name = "id", unique = true)
	private Integer id;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "attendee")
	private List<Ticket> tickets = new ArrayList<Ticket>();

	@Column(name = "objectToken", nullable = true)
	private String objectToken;

	@Column(name = "gender", nullable = true)
	private String gender;

	@Column(name = "age", nullable = true)
	private int age;

	@Lob
	@Column(name = "image64bit", nullable = false)
	private byte[] image64bit;

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

	public byte[] getImage64bit() {
		return image64bit;
	}

	public void setImage64bit(byte[] image64bit) {
		this.image64bit = image64bit;
	}

	public String getObjectToken() {
		return objectToken;
	}

	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
