package com.rest.ctr;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.dto.Ownership;
import com.rest.dto.Product;
import com.rest.dto.ProductRatings;
import com.rest.srv.OwnershipRepository;
import com.rest.srv.ProductRepository;
import com.rest.srv.RatingRepository;
import com.rest.srv.UserRepository;

@RestController
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private OwnershipRepository ownershipRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("/products/{id}")
	public ProductRatings productById(@PathVariable String id) {
		return new ProductRatings(productRepository.findById(id),ratingRepository.findAllByProductCode(id));
	}
	
	@GetMapping("/products")
	public Page<Product> loadProductPage(@RequestParam(required = false)String code, Pageable pageable){
		if(code==null)
			code="";
		return productRepository.findAllProductsWithPagination(code, pageable);
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> newProduct(@Valid @RequestBody Product product) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		
		Map<String, String> response = new HashMap<>();
		
		if(userRepository.existsById(currentUserName)) {
			if(product.getCode()!=null) {
				if(!productRepository.existsById(product.getCode())) {
					productRepository.save(product);
					ownershipRepository.save(new Ownership(currentUserName, product.getCode()));
					
					response.put("status", "200");
					response.put("messege","Product saved.");
					return ResponseEntity.ok(response);
				}
				else {
					response.put("status", "400");
					response.put("error", "Bad request");
					response.put("messege","Product with this code already exists.");
					return ResponseEntity.status(400).body(response);
				}
			}
			else {
				response.put("status", "400");
				response.put("error", "Bad request");
				response.put("messege","Missing product code.");
				return ResponseEntity.status(400).body(response);
			}
		}
		else {
			response.put("status", "401");
			response.put("error", "Authorization failed.");
			response.put("messege","No such user.");
			return ResponseEntity.status(401).body(response);
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product,@PathVariable("id") String id) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		
		Map<String, String> response = new HashMap<>();    
		
		if(userRepository.existsById(currentUserName)) {
			if(productRepository.existsById(id)) {
				Ownership ownership = ownershipRepository.findById(id).get();
				if(ownership.getUserEmail().equals(currentUserName)) {
					product.setCode(id);
					productRepository.save(product);
				}
				else {
					response.put("status", "401");
					response.put("error", "Authorization failed.");
					response.put("messege","Not the owner of this product.");
					return ResponseEntity.status(401).body(response);
				}
				response.put("status", "200");
				response.put("messege","Product updated!");
				return ResponseEntity.ok(response);
			}
			else 
				response.put("status", "404");
				response.put("error", "Not found");
				response.put("messege","No such product.");
				return ResponseEntity.status(404).body(response);
		}
		else {
			response.put("status", "401");
			response.put("error", "Authorization failed.");
			response.put("messege","No such user.");
			return ResponseEntity.status(401).body(response);
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
