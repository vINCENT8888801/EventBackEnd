package com.fyp.eventBackend.Common;

public enum UserRoleEnum {
	
	ATTENDEE("Attendee","ATTENDEE"),
	ADMIN("Admin","ADMIN");
	
	private String value;
	private String dBValue;
	
	private UserRoleEnum(String value,String dBValue){  
		this.value=value;  
		this.dBValue = dBValue;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getDBValue() {
		return this.dBValue;
	}

}
