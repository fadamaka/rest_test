package com.rest.srv;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rest.dto.Product;
public interface ProductRepository extends CrudRepository<Product, String>{
	
	@Query(value = "SELECT p FROM Product p WHERE id like %:code% ORDER BY id")
	Page<Product> findAllProductsWithPagination(@Param("code") String code, Pageable pageable);

}
