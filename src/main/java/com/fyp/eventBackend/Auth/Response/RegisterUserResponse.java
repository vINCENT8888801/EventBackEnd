package com.fyp.eventBackend.Auth.Response;

import com.fyp.eventBackend.Database.User;

public class RegisterUserResponse {
	
	User newUser;

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	
}
