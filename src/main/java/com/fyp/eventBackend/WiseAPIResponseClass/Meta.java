package com.fyp.eventBackend.WiseAPIResponseClass;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Meta {
	private String requestId;
	private String timestamp;
	
	public String getRequestId() {
		return requestId;
	}
	@JsonSetter
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	@JsonSetter
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
