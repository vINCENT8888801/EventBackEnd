package com.fyp.eventBackend.WiseAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fyp.eventBackend.WiseAPIResponseClass.Meta;


public abstract class WiseAPIResponseBase {

	private boolean success;
	private String code;
	private String message;
	private int size;
	private Meta meta;

	public boolean isSuccess() {
		return success;
	}

	@JsonSetter
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	@JsonSetter
	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	@JsonSetter
	public void setMessage(String message) {
		this.message = message;
	}

	public int getSize() {
		return size;
	}

	@JsonSetter
	public void setSize(int size) {
		this.size = size;
	}

	public Meta getMeta() {
		return meta;
	}

	@JsonSetter
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
