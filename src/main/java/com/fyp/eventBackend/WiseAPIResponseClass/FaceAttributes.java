package com.fyp.eventBackend.WiseAPIResponseClass;

import com.fasterxml.jackson.annotation.JsonSetter;

public class FaceAttributes {
	private String gender;
	private int age;
	
	public String getGender() {
		return gender;
	}
	@JsonSetter
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	@JsonSetter
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
