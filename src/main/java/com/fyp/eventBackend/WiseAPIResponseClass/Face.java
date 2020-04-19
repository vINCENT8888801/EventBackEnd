package com.fyp.eventBackend.WiseAPIResponseClass;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Face {
	private FaceLocate faceLocate;
	private FaceAttributes attributes;
	private ArrayList<FaceLandmark> landmark;
	private String objectToken;
	
	public FaceLocate getFaceLocate() {
		return faceLocate;
	}
	@JsonSetter
	public void setFaceLocate(FaceLocate faceLocate) {
		this.faceLocate = faceLocate;
	}
	public FaceAttributes getAttributes() {
		return attributes;
	}
	@JsonSetter
	public void setAttributes(FaceAttributes attributes) {
		this.attributes = attributes;
	}
	public ArrayList<FaceLandmark> getLandmark() {
		return landmark;
	}
	@JsonSetter
	public void setLandmark(ArrayList<FaceLandmark> landmark) {
		this.landmark = landmark;
	}
	public String getObjectToken() {
		return objectToken;
	}
	@JsonSetter
	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}
}
