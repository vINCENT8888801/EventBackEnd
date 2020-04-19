package com.fyp.eventBackend.WiseAPIResponseClass;

import com.fasterxml.jackson.annotation.JsonSetter;

public class FaceLocate {
	private int left;
	private int top;
	private int width;
	private int height;
	
	public int getLeft() {
		return left;
	}
	@JsonSetter
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	@JsonSetter
	public void setTop(int top) {
		this.top = top;
	}
	public int getWidth() {
		return width;
	}
	@JsonSetter
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	@JsonSetter
	public void setHeight(int height) {
		this.height = height;
	}
	
}
