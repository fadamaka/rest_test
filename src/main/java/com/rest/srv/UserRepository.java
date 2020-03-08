package com.rest.srv;

import org.springframework.data.repository.CrudRepository;

import com.rest.dto.User;

public interface UserRepository extends CrudRepository<User, String>{
	
	User findByEmail(String email);
}
