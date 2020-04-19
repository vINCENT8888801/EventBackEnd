package com.fyp.eventBackend.WebSocketConfiguration;

public class FaceSearchSocketResponse {
	
	private String name;
	private String objectToken;
	private String imgString;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjectToken() {
		return objectToken;
	}
	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}
	public String getImgString() {
		return imgString;
	}
	public void setImgString(String imgString) {
		this.imgString = imgString;
	}
	
}
