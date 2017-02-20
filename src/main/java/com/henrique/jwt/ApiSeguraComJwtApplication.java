package com.henrique.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration
public class ApiSeguraComJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSeguraComJwtApplication.class, args);
	}
}
