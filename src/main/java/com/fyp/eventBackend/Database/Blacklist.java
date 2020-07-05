package com.fyp.eventBackend.Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Blacklist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name="blacklist_generator", sequenceName = "blacklist_seq")
	@Column(name="id", unique=true)
	private Integer id;
	
	@Column(name = "objectToken", nullable = true)
	private String objectToken;
	
	@Column(name = "gender", nullable = true)
	private String gender;
	
	@Column(name = "age", nullable = true)
	private int age;

}
