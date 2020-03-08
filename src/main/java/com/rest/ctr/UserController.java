package com.rest.ctr;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.dto.User;
import com.rest.srv.UserRepository;

@RestController
public class UserController {
	
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserController(UserRepository userRepositroy, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepositroy;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	} 
	
	@PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) {
		Map<String, String> response = new HashMap<>();    
		
        user.setPw(bCryptPasswordEncoder.encode(user.getPw()));
        if(!userRepository.existsById(user.getEmail())) {
        	userRepository.save(user);
        	
			response.put("status", "200");
			response.put("messege","Registration successful!");
        	return ResponseEntity.ok(response);
        }
        response.put("status", "400");
		response.put("error", "Bad request");
		response.put("messege","User already exits with this email address.");
        return ResponseEntity.badRequest().body(response);
        
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
