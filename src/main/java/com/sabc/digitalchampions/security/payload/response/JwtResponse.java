package com.sabc.digitalchampions.security.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String lastName;
	private String firstName;
	private String username;
	private final List<String> roles;

	public JwtResponse(String accessToken, String lastName, String firstName, String username, List<String> roles) {
		this.token = accessToken;
		this.lastName = lastName;
		this.firstName = firstName;
		this.username = username;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public JwtResponse setToken(String token) {
		this.token = token;
		return this;
	}

	public String getType() {
		return type;
	}

	public JwtResponse setType(String type) {
		this.type = type;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}
