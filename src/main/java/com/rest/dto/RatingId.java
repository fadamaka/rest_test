package com.rest.dto;

import java.io.Serializable;
import java.util.Objects;

public class RatingId implements Serializable{

	private static final long serialVersionUID = 1L;

	private String productCode;
	private String userEmail;
	
	public RatingId() {
		
	}
	
	public RatingId(String productCode, String userEmail) {
		this.productCode = productCode;
		this.userEmail = userEmail;
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
	
	@Override
    public int hashCode() {
        return Objects.hash(productCode, userEmail);
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RatingId other = (RatingId) obj;
        return Objects.equals(this.productCode, other.getProductCode())
                && Objects.equals(this.userEmail, other.getUserEmail());
    }
	// TODO: equals() and hashCode()
	
	
	
}
