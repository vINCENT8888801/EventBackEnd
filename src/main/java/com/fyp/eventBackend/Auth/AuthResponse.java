package com.fyp.eventBackend.Auth;

import com.fyp.eventBackend.Common.BasicHttpResponse;

public class AuthResponse extends BasicHttpResponse{
	
	private  String jwt;
	
	public AuthResponse() {
	}
	
	public AuthResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

}
