package com.fyp.eventBackend.WebSocketConfiguration.FrontEndRequest;

import com.fasterxml.jackson.annotation.JsonSetter;

public class getObjectTokenRequest {
	
	private String imgString;

	public String getImgString() {
		return imgString;
	}

	public void setImgString(String imgString) {
		this.imgString = imgString;
	}
	
}
