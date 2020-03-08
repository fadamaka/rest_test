package com.rest.srv;

import org.springframework.data.repository.CrudRepository;

import com.rest.dto.Ownership;

public interface OwnershipRepository extends CrudRepository<Ownership, String> {

}
