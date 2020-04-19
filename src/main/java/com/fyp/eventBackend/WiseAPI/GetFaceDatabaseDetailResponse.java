 package com.fyp.eventBackend.WiseAPI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.WiseAPIResponseClass.FailureReason;
import com.fyp.eventBackend.WiseAPIResponseClass.Library;

public class GetFaceDatabaseDetailResponse extends WiseAPIResponseBase{
	
	private String libraryId;
	private String libraryName;
	private float thresholds;
	private List<String> objectTokens;
	
	public String getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId;
	}
	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	public float getThresholds() {
		return thresholds;
	}
	public void setThresholds(float thresholds) {
		this.thresholds = thresholds;
	}
	public List<String> getObjectTokens() {
		return objectTokens;
	}
	public void setObjectTokens(List<String> objectTokens) {
		this.objectTokens = objectTokens;
	}
	
	@SuppressWarnings("unchecked")
	@JsonProperty("collection")
	private void unpackCollection(List<Map<String,Object>> collection) {
		objectTokens = new ArrayList<String>();
		
		LinkedHashMap<String, Object> libraryArray =  (LinkedHashMap<String, Object>) collection.get(0);
		System.out.println(libraryArray);
		this.libraryId =   (String) libraryArray.get("libraryId");
		this.libraryName =   (String) libraryArray.get("libraryName");
		this.thresholds =   (float) libraryArray.get("thresholds");
		objectTokens =  (List<String>) libraryArray.get("objectTokens");

	}

}
