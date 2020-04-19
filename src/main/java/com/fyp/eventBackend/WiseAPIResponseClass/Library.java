package com.fyp.eventBackend.WiseAPIResponseClass;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Library {
	
	private String libraryId;
	private String LibraryName;
	private float thresholds;
	
	
	public String getLibraryId() {
		return libraryId;
	}
	@JsonSetter
	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId;
	}
	public String getLibraryName() {
		return LibraryName;
	}
	@JsonSetter
	public void setLibraryName(String libraryName) {
		LibraryName = libraryName;
	}
	public float getThresholds() {
		return thresholds;
	}
	@JsonSetter
	public void setThresholds(float thresholds) {
		this.thresholds = thresholds;
	}
	
	

}
