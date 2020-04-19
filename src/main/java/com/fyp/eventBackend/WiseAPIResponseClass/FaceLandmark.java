package com.fyp.eventBackend.WiseAPIResponseClass;

import com.fasterxml.jackson.annotation.JsonSetter;

public class FaceLandmark {
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	@JsonSetter
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	@JsonSetter
	public void setY(int y) {
		this.y = y;
	}
}
