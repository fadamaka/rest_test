package com.rest.dto;

import java.util.Optional;

public class ProductRatings {
	
	private Optional<Product> product;
	private Iterable<Rating> ratings;
	
	public ProductRatings() {
		
	}

	public ProductRatings(Optional<Product> product,Iterable<Rating> ratings) {
		this.product = product;
		this.ratings = ratings;
	}

	public Optional<Product> getProduct() {
		return product;
	}

	public void setProduct(Optional<Product> product) {
		this.product = product;
	}

	public Iterable<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Iterable<Rating> ratings) {
		this.ratings = ratings;
	}
	
	
}
