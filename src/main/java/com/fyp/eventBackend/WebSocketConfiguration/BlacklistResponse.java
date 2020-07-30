package com.fyp.eventBackend.WebSocketConfiguration;

import com.fyp.eventBackend.Database.Blacklist;

public class BlacklistResponse {

	private Blacklist blacklist;

	private String image64;

	private String dbImage64;

	public Blacklist getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}

	public String getImage64() {
		return image64;
	}

	public void setImage64(String image64) {
		this.image64 = image64;
	}

	public String getDbImage64() {
		return dbImage64;
	}

	public void setDbImage64(String dbImage64) {
		this.dbImage64 = dbImage64;
	}

}
