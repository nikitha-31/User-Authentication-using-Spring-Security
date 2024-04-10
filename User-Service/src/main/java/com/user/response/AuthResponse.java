package com.user.response;

public class AuthResponse {
	
	private String jwt;
	private String message;
	private Boolean status;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public AuthResponse(String jwt, String message, Boolean status) {
		super();
		this.jwt = jwt;
		this.message = message;
		this.status = status;
	}
	public AuthResponse() {
		super();
	}
	
}
