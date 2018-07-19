package com.sjtusummerproject.cartmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class CartmicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartmicroserviceApplication.class, args);
	}
}
