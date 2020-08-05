package com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class RegisterWalkInRequest {
	
	private int eventId;
    private String email;
    private String name;
    private String password;
    private String image64bit;
    private String objToken;
    private int age;
    private String gender;
    private float temperature;
    
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage64bit() {
		return image64bit;
	}
	public void setImage64bit(String image64bit) {
		this.image64bit = image64bit;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getObjToken() {
		return objToken;
	}
	public void setObjToken(String objToken) {
		this.objToken = objToken;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	
}