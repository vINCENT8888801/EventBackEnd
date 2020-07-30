package com.fyp.eventBackend.BlackList.Response;

import com.fyp.eventBackend.Common.BasicHttpResponse;
import com.fyp.eventBackend.Database.Blacklist;

public class CreateBlacklistResponse extends BasicHttpResponse{
	
	private Blacklist newBlacklist;
	
	private String image64bit;

	public Blacklist getNewBlacklist() {
		return newBlacklist;
	}

	public void setNewBlacklist(Blacklist newBlacklist) {
		this.newBlacklist = newBlacklist;
	}

	public String getImage64bit() {
		return image64bit;
	}

	public void setImage64bit(String image64bit) {
		this.image64bit = image64bit;
	}

	
}
