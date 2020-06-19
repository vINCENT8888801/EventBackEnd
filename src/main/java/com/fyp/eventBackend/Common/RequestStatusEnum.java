package com.fyp.eventBackend.Common;

public enum RequestStatusEnum {
	
	SUCCESS("SUCCESS"),
	FAILED("FAILED");
	
	private String value;
	private String dBValue;
	
	private RequestStatusEnum(String value){  
		this.value=value;  
	}
	
	public String getValue() {
		return this.value;
	}
	


}
