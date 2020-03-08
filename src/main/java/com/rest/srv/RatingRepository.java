package com.rest.srv;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rest.dto.Rating;
import com.rest.dto.RatingId;

public interface RatingRepository extends CrudRepository<Rating, RatingId>{
	
	List<Rating> findAllByProductCode(String productCode);
	
	@Query("SELECT avg(r.rating) FROM Rating r WHERE r.productCode= ?1")
	Double avgRatingByProductCode(String productCode);
}
