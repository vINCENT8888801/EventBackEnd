package com.fyp.eventBackend.WiseAPIResponseClass;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FaceSearchResult {
	
	private float confidence;
	private FaceLocate faceLocate2;
	private String imageUrl;
	private String objectToken;
	
	public float getConfidence() {
		return confidence;
	}
	@JsonSetter
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}
	
	public FaceLocate getFaceLocate2() {
		return faceLocate2;
	}
	public void setFaceLocate2(FaceLocate faceLocate2) {
		this.faceLocate2 = faceLocate2;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	@JsonSetter
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getObjectToken() {
		return objectToken;
	}
	@JsonSetter
	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}
	
	@SuppressWarnings("unchecked")
	@JsonProperty("faceLocate")
	private void unpackCollection(Object obj) {
		if(obj.getClass().equals(String.class)) {
			
		}else {
			this.faceLocate2 = new ObjectMapper().convertValue(obj, FaceLocate.class);
		}
	}
	
}
