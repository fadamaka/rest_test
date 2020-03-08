package com.rest.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ownership {
	
	private String userEmail;
	@Id
	private String productCode;
	
	public Ownership() {
		
	}

	public Ownership(String userEmail, String productCode) {
		this.userEmail = userEmail;
		this.productCode = productCode;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
}
