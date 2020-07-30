package com.fyp.eventBackend.Auth.Request;

import java.io.File;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder
public class ValidatePictureRequest {

	private String image64;

	public String getImage64() {
		return image64;
	}

	public void setImage64(String image64) {
		this.image64 = image64;
	}

}
