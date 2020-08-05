package com.fyp.eventBackend.WebSocketConfiguration;

public class FaceSearchSocketResponse {
	
	
	private String name;
	private String gender;
	private int age;
	private String objectToken;
	private String imgString;
	private float accuracy;
	private int ticketId;
	private String attendanceStatus;
	private boolean alreadyRegistered;
	
	public float getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjectToken() {
		return objectToken;
	}
	public void setObjectToken(String objectToken) {
		this.objectToken = objectToken;
	}
	public String getImgString() {
		return imgString;
	}
	public void setImgString(String imgString) {
		this.imgString = imgString;
	}
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isAlreadyRegistered() {
		return alreadyRegistered;
	}
	public void setAlreadyRegistered(boolean alreadyRegistered) {
		this.alreadyRegistered = alreadyRegistered;
	}
	public String getAttendanceStatus() {
		return attendanceStatus;
	}
	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}
	
	
	
}
