package com.fyp.eventBackend.EventGeneration.Response;

import com.fyp.eventBackend.Common.BasicHttpResponse;

public class EventReportResponse extends BasicHttpResponse{

	private String genderData;
	
	private String attendanceData;
	
	private String temperatureData;

	public String getGenderData() {
		return genderData;
	}

	public void setGenderData(String genderData) {
		this.genderData = genderData;
	}

	public String getAttendanceData() {
		return attendanceData;
	}

	public void setAttendanceData(String attendanceData) {
		this.attendanceData = attendanceData;
	}

	public String getTemperatureData() {
		return temperatureData;
	}

	public void setTemperatureData(String temperatureData) {
		this.temperatureData = temperatureData;
	}
	
}
