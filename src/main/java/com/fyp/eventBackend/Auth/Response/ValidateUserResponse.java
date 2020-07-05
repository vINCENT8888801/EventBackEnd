package com.fyp.eventBackend.Auth.Response;

import com.fyp.eventBackend.Common.BasicHttpResponse;

public class ValidateUserResponse extends BasicHttpResponse{
	
	private boolean uniqueName;
	
	private boolean uniqueEmail;

	public boolean isUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(boolean uniqueName) {
		this.uniqueName = uniqueName;
	}

	public boolean isUniqueEmail() {
		return uniqueEmail;
	}

	public void setUniqueEmail(boolean uniqueEmail) {
		this.uniqueEmail = uniqueEmail;
	}
	
}
