package com.rest.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ComponentScan("com.rest")
@EntityScan("com.rest")
@EnableJpaRepositories("com.rest")
@SpringBootApplication
public class RestTestApplication {
	
	 @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
	public static void main(String[] args) {
		SpringApplication.run(RestTestApplication.class, args);
	}

}
