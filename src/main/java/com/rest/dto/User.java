package com.rest.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
	
	@Id
	@Email(message = "Email should be valid!")
	private String email;
	@NotBlank(message = "Name is mandatory!")
	private String name;
	@NotBlank(message = "Password must not be empty!")
	private String pw;
	
	public User() {
	}


	public User(String email, String name, String pw) {
		this.email = email;
		this.name = name;
		this.pw = pw;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	

}
