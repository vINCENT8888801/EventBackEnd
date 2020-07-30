package com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest;

import com.fasterxml.jackson.annotation.JsonSetter;

public class FaceSearchRequest {
	
	private String id;
	private String imgString;
	
	public String getId() {
		return id;
	}
	@JsonSetter
	public void setId(String id) {
		this.id = id;
	}
	public String getImgString() {
		return imgString;
	}
	@JsonSetter
	public void setImgString(String imgString) {
		this.imgString = imgString;
	}
	
	
}
