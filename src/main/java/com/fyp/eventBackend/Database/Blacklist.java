package com.fyp.eventBackend.Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Blacklist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name="blacklist_generator", sequenceName = "blacklist_seq")
	@Column(name="id", unique=true)
	private Integer id;
	
	@Column(name = "objectToken", nullable = true)
	private String objectToken;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "gender", nullable = true)
	private String gender;
	
	@Lob
	@Column(name = "image64bit", nullable = false)
	@JsonIgnore
	private byte[] image64bit;
	
	@Column(name = "age", nullable = true)
	private int age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObjectToken() {
		return objectToken;
	}

	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public byte[] getImage64bit() {
		return image64bit;
	}

	public void setImage64bit(byte[] image64bit) {
		this.image64bit = image64bit;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	

}
