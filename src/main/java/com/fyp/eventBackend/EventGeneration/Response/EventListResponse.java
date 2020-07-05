package com.fyp.eventBackend.EventGeneration.Response;

import java.util.List;

import com.fyp.eventBackend.Common.BasicHttpResponse;
import com.fyp.eventBackend.Database.Event;

public class EventListResponse extends BasicHttpResponse{
	
	private List<Event> eventList;
	
	private long totalPage;

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	
	

}
