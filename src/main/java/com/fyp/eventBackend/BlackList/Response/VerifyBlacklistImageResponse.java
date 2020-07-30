package com.fyp.eventBackend.BlackList.Response;

import javax.persistence.Column;
import javax.persistence.Lob;

import com.fyp.eventBackend.Common.BasicHttpResponse;
import com.fyp.eventBackend.Database.Blacklist;

public class VerifyBlacklistImageResponse extends BasicHttpResponse{
	
	private String gender;
	
	private String image64bit;
	
	private String objToken;
	
	private int age;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getObjToken() {
		return objToken;
	}

	public void setObjToken(String objToken) {
		this.objToken = objToken;
	}
	
}