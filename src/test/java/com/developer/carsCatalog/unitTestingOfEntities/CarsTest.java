package com.developer.carsCatalog.unitTestingOfEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.entities.Make;


public class CarsTest {

	private Cars car;
	private Make make;
	

	@BeforeEach
	public void setUp() {
		make = new Make();
		make.setName("Toyota");
		make.setCnpj("12.345.678/0001-99");
		

		

		car = new Cars();
		car.setId(1L);
		car.setModel("Corolla");
		car.setYears(2020);
		car.setMake(make);
		

	}

	@Test
	public void testCarModel() {
		assertEquals("Corolla", car.getModel());
	}

	@Test
	public void testCarYears() {
		assertEquals(2020, car.getYears());
	}

	@Test
	public void testCarMake() {
		assertEquals("Toyota", car.getMake().getName());
	}

	

}


