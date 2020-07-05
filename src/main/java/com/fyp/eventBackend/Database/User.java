package com.fyp.eventBackend.Database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity // This tells Hibernate to make a table out of this class
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "user_seq", allocationSize=50)
	@Column(name="id", unique=true)
	private Integer id;

	@Column(name="name", unique=true, nullable = false)
	private String name;

	@Column(name="email", unique=true, nullable = false)
	private String email;

	@Column(name="password", nullable = false)
	private String password;

	@Column(name="role", nullable = false)
	private String role;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Attendee attendee;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Attendee getAttendee() {
		return attendee;
	}

	public void setAttendee(Attendee attendee) {
		this.attendee = attendee;
	}
	
	

}
