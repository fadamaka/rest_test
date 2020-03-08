package com.rest.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.Formula;

@Entity
public class Product {

	@Id
	@Pattern(regexp="[\\d]{6}", message = "Product Code must be numeric and 6 digits.")
	private String code;
	@NotBlank(message = "Name is mandatory!")
	private String name;
	private String description;
	@NotNull
	@Positive
	private Double price;
	@Formula("SELECT AVG(r.rating) FROM rating r WHERE code=r.product_code")
	private Double rating;

	public Product() {
	}

	public Product(String code, String name, String description, Double price, Double rating) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.price = price;
		this.rating = rating;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

}
