package com.rest.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@IdClass(RatingId.class)
public class Rating{
	
	@JsonIgnore
	@Id
	private String productCode;
	@Id
	private String userEmail;
	@Min(value=1)
	@Max(value=10)
	private Integer rating;
	
	public Rating() {
		
	}

	public Rating(String productCode, String userEmail, Integer rating) {
		this.productCode = productCode;
		this.userEmail = userEmail;
		this.rating = rating;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	
}
