package com.springrestweather.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UpdateUserRequestDTO {
	@NotBlank
	private String id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@Email
	@NotBlank
	private String email;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
