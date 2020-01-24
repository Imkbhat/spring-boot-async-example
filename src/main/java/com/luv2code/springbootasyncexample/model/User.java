package com.luv2code.springbootasyncexample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	
	private String user;
	private String name;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String user, String name) {
		this.user = user;
		this.name = name;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [user=" + user + ", name=" + name + "]";
	}
	
}
