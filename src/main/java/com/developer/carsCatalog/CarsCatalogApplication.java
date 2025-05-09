package com.developer.carsCatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.developer.carsCatalog"})

public class CarsCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsCatalogApplication.class, args);
	}

}
