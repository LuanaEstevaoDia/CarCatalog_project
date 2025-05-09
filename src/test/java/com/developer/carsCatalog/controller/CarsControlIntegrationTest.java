package com.developer.carsCatalog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.developer.carsCatalog.CarsCatalogApplication;
import com.developer.carsCatalog.controllers.CarsController;
import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.repositories.CarsRepository;
import com.developer.carsCatalog.services.CarsService;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = CarsCatalogApplication.class)
public class CarsControlIntegrationTest {

	@Autowired
	CarsController carsController;
	
	@InjectMocks
	private CarsService carsService;

	@MockitoBean
	CarsRepository carsRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void shouldThrowExceptionWhenModelIsNullOrEmpty() {
		Cars car = Cars.builder()
				.model("")
				.years(2020)
				.chassi("1NXBR32E76Z123456")
				.build();
		
		Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
			carsService.validateCar(car);
		
		});
		assertEquals("O campo modelo é obrigatório", exception.getMessage());
	}
	
	@Test
	public void shouldThrowExceptionWhenYearIsInvalid() {
		 int currentYear = LocalDate.now().getYear();
		 Cars car = Cars.builder()
				 .model("Corolla")
				 .years(currentYear + 1)
				 .chassi("1NXBR32E76Z123456")
				 .build();
		 
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			carsService.validateCar(car);
		});	
		
		assertEquals("Ano do veículo inválido", exception.getMessage());
				 	
	}
	
	public void shouldThrowExceptionWhenChassiIsInvalid() {
		Cars car = Cars.builder()
				.model("Corolla")
				.years(2020)
				.chassi("123456")
				.build();
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			carsService.validateCar(car);
		});
		assertEquals("Número de chassi inválido", exception.getMessage());
		
	}
	
	 @Test
	    public void shouldThrowExceptionWhenChassiIsDuplicate() {
	        Cars car = Cars.builder()
	                .model("Corolla")
	                .years(2020)
	                .chassi("1NXBR32E76Z123456")
	                .build();

	        when(carsRepository.existsByChassi(car.getChassi())).thenReturn(true); 

	        Exception exception = assertThrows(DuplicateKeyException.class, () -> {
	            carsService.validateCar(car);
	        });

	        assertEquals("Chassi já existente", exception.getMessage());
	    }

	    @Test
	    public void shouldPassValidationForValidCar() {
	        Cars car = Cars.builder()
	                .model("Corolla")
	                .years(2020)
	                .chassi("1NXBR32E76Z123456")
	                .build();

	        when(carsRepository.existsByChassi(car.getChassi())).thenReturn(false);

	        
	        carsService.validateCar(car);


	

	    }
}
