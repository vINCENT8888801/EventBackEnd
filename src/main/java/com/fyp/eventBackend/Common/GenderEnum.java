package com.fyp.eventBackend.Common;

public enum GenderEnum {
	
	MALE("Male","MALE","Male"),
	FEMALE("Female","FEMALE","Female");
	
	private String value;
	private String dBValue;
	private String APIValue;
	
	private GenderEnum(String value,String dBValue,String APIValue){  
		this.value=value;  
		this.dBValue = dBValue;
		this.APIValue = APIValue;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getDBValue() {
		return this.dBValue;
	}
	
	public String getAPIValue() {
		return this.APIValue;
	}
	
	public static GenderEnum getEnumWithAPIValue(String APIValue) {
		for (GenderEnum curEnum : GenderEnum.values()) {
			  if(curEnum.APIValue.equals(APIValue))
				  return curEnum;
		}
		return null;
	}
	
	public static GenderEnum getEnumwithValue(String value) {
		for (GenderEnum curEnum : GenderEnum.values()) {
			  if(curEnum.value.equals(value))
				  return curEnum;
		}
		return null;
	}
	
	public static GenderEnum getEnumwithdbValue(String value) {
		for (GenderEnum curEnum : GenderEnum.values()) {
			  if(curEnum.dBValue.equals(value))
				  return curEnum;
		}
		return null;
	}


}

