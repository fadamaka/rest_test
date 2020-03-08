package com.rest.ctr;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.dto.Rating;
import com.rest.srv.ProductRepository;
import com.rest.srv.RatingRepository;
import com.rest.srv.UserRepository;

@RestController
public class RatingController {
	
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/ratings/{productCode}")
	public Iterable<Rating> getRatingsByProductCode (@PathVariable String productCode){
		return ratingRepository.findAllByProductCode(productCode);
	}
	
	@GetMapping("/ratings/avg/{productCode}")
	public ResponseEntity<?> getAvgRatingByProductCode (@PathVariable String productCode){
		Map<String, Double> response = new HashMap<>();
		response.put("rating",ratingRepository.avgRatingByProductCode(productCode));
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/rate/{productCode}")
	public ResponseEntity<?> saveRating(@Valid @RequestBody Rating rating,@PathVariable("productCode") String productCode){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		
		Map<String, String> response = new HashMap<>();
		
		rating.setProductCode(productCode);
		rating.setUserEmail(currentUserName);
		
		if(productRepository.existsById(productCode)) {
			if(userRepository.existsById(currentUserName)) {
				ratingRepository.save(rating);
				
				response.put("status", "200");
				response.put("messege","Rating saved!");
				return ResponseEntity.ok(response);
			}
			else {
				response.put("status", "401");
				response.put("error", "Authorization failed.");
				response.put("messege","No such user.");
				return ResponseEntity.status(401).body(response);
			}
		}
		else {
			response.put("status", "404");
			response.put("error", "Not found");
			response.put("messege","No such product.");
			return ResponseEntity.status(404).body(response);
		}
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public  Map<String, String> handleBadRequest(
			HttpMessageNotReadableException ex) {
	    Map<String, String> errors = new HashMap<>();
	    errors.put("error", "Bad request");
	    errors.put("status","400");
	    
	    return errors;
	}
}
