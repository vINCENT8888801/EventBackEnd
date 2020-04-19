package com.fyp.eventBackend.WiseAPIResponseClass;

import com.fasterxml.jackson.annotation.JsonSetter;

public class FailureReason {
	
	private String errorMsg;
	private String objectToken;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	@JsonSetter
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getObjectToken() {
		return objectToken;
	}
	@JsonSetter
	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}
	

}
