package com.fyp.eventBackend.BlackList.Request;

public class UpdateBlackListRequest {

	private int id;

	private String objectToken;

	private String name;

	private String gender;

	private boolean isImgChanged;
	
	private String image64bit;

	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public boolean isImgChanged() {
		return isImgChanged;
	}

	public void setImgChanged(boolean isImgChanged) {
		this.isImgChanged = isImgChanged;
	}
}
