package com.fyp.eventBackend.WiseAPI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.eventBackend.WiseAPIResponseClass.FailureReason;

public class RemoveSelectedFaceFromDatabaseResponse extends WiseAPIResponseBase{
	
	private List<FailureReason> failureReason;
	
	private String libraryId;
	
	private int objectCount;
	
	private int objectSuccessCount;
	
	
	
	public List<FailureReason> getFailureReason() {
		return failureReason;
	}



	public void setFailureReason(List<FailureReason> failureReason) {
		this.failureReason = failureReason;
	}



	public String getLibraryId() {
		return libraryId;
	}



	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId;
	}



	public int getObjectCount() {
		return objectCount;
	}



	public void setObjectCount(int objectCount) {
		this.objectCount = objectCount;
	}



	public int getObjectSuccessCount() {
		return objectSuccessCount;
	}



	public void setObjectSuccessCount(int objectSuccessCount) {
		this.objectSuccessCount = objectSuccessCount;
	}



	@SuppressWarnings("unchecked")
	@JsonProperty("collection")
	private void unpackCollection(List<Map<String,Object>> collection) {
		failureReason = new ArrayList<FailureReason>();
		
		LinkedHashMap<String, Object> libraryArray =  (LinkedHashMap<String, Object>) collection.get(0);
		System.out.println(libraryArray);
		this.libraryId =   (String) libraryArray.get("libraryId");
		this.objectCount =   (int) libraryArray.get("objectCount");
		this.objectSuccessCount =   (int) libraryArray.get("objectSuccessCount");
		List<Map<String,Object>>failureReasons =  (List<Map<String, Object>>) libraryArray.get("failureReason");
		failureReasons.forEach((failureReasonMap)->{
			FailureReason newFailureReason = new ObjectMapper().convertValue(failureReasonMap, FailureReason.class);
			this.failureReason.add(newFailureReason);
		});
	}

}
