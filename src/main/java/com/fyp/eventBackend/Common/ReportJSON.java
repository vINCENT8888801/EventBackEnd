package com.fyp.eventBackend.Common;

public class ReportJSON {
	
	private String name;
	
	private int value;

	public ReportJSON(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public ReportJSON() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
