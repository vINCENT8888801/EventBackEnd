package com.fyp.eventBackend.WiseAPI;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteFaceDatabaseResponse extends WiseAPIResponseBase{
	
	private String libraryId;
	
	public String getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId;
	}

	@SuppressWarnings("unchecked")
	@JsonProperty("collection")
	private void unpackCollection(List<Map<String,Object>> collection) {
		
		
		LinkedHashMap<String, Object> libraryArray =  (LinkedHashMap<String, Object>) collection.get(0);
		System.out.println(libraryArray);
		this.libraryId =   (String) libraryArray.get("libraryId");
	}

}