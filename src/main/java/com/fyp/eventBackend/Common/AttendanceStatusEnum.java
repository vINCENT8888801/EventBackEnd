package com.fyp.eventBackend.Common;

public enum AttendanceStatusEnum {
	PRESENT("PRESENT"),
	ABSENT("ABSENT");
	
	private String value;
	
	private AttendanceStatusEnum(String value){  
		this.value=value;  
	}
	
	public String getValue() {
		return this.value;
	}
	
}
