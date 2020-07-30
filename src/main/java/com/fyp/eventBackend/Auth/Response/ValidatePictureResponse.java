package com.fyp.eventBackend.Auth.Response;

import com.fyp.eventBackend.Common.BasicHttpResponse;

public class ValidatePictureResponse extends BasicHttpResponse {

	private String image64bit;

	private String objToken;
	
	private int age;
	
	private String gender;

	public String getImage64bit() {
		return image64bit;
	}

	public void setImage64bit(String image64bit) {
		this.image64bit = image64bit;
	}

	public String getObjToken() {
		return objToken;
	}

	public void setObjToken(String objToken) {
		this.objToken = objToken;
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
	
	
}
