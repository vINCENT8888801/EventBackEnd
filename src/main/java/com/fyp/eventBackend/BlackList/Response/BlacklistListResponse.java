package com.fyp.eventBackend.BlackList.Response;

import java.util.List;

import com.fyp.eventBackend.Common.BasicHttpResponse;
import com.fyp.eventBackend.Database.Blacklist;
import com.fyp.eventBackend.Database.Event;

public class BlacklistListResponse extends BasicHttpResponse{
	
	private List<Blacklist> blacklistList;
	
	private long totalPage;

	public List<Blacklist> getBlacklistList() {
		return blacklistList;
	}

	public void setBlacklistList(List<Blacklist> blacklistList) {
		this.blacklistList = blacklistList;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	
	

}
