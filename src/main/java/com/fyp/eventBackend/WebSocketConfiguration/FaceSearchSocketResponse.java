package com.fyp.eventBackend.WebSocketConfiguration;

public class FaceSearchSocketResponse {
	
	private String name;
	private String objectToken;
	private String imgString;
	private float accuracy;
	
	
	public float getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
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
