package com.developer.carsCatalog.unitTestingOfControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.developer.carsCatalog.controllers.CarsController;
import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.services.CarsService;


@ActiveProfiles("test")
public class CarsControllerTest {
	
	@InjectMocks
	CarsController carsController;
	
	@Mock
	CarsService carsService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void saveSuccessTest() {

		Make make = Make.builder().name("Chevrolet").cnpj("CNPJ: 45.678.912/0001-34").build();

		Cars carRequest = Cars.builder().model("Onix").years(2020).chassi("1NXBR32E76Z123456").make(make).build();
		
		Cars carResponse = Cars.builder().id(1L).model("Onix").years(2020).chassi("1NXBR32E76Z123456").make(make).build();

		when(carsService.saveCar(carRequest)).thenReturn(carResponse);
		
		ResponseEntity<Map<String, Object>> response = carsController.saveCar(carRequest);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Veículo salvo com sucesso", response.getBody().get("message"));
		assertEquals(carResponse, response.getBody().get("car"));
	
		
	}
	
	@Test
	void testToGetListAllCars() {
		Make make = Make.builder().name("Chevrolet").cnpj("CNPJ: 45.678.912/0001-34").build();
		Cars car = Cars.builder().id(1L).model("Onix").years(2020).chassi("1NXBR32E76Z123456").make(make).build();
		List<Cars> getCars = List.of(car);
		
		when(carsService.findAll()).thenReturn(getCars);
		ResponseEntity<List<Cars>> response = carsController.getAllCars();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
		assertEquals("Onix", response.getBody().get(0).getModel());
		
		
		
		
	}
	
	@Test
	void testToGetIdCar() {
		
		Make make = Make.builder().name("Fiat").cnpj("CNPJ: 46.678.912/0001-34").build();
		Cars car = Cars.builder().id(2L).model("Uno").years(2017).chassi("1NXBR32E76Z123487").make(make).build();
		

		when(carsService.findByIdOrThrow(2L)).thenReturn(car);
		ResponseEntity<Cars> response = carsController.getCarById(2L);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Uno", response.getBody().getModel());
		assertEquals(2017, response.getBody().getYears());
		assertEquals("1NXBR32E76Z123487", response.getBody().getChassi());
		assertEquals("Fiat", response.getBody().getMake().getName());
		
	}
	
	@Test
	void testToUpdateIdCar() {
		
		Make make = Make.builder().name("Fiat").cnpj("CNPJ: 46.678.912/0001-34").build();
		Cars car = Cars.builder().id(2L).model("Uno").years(2017).chassi("1NXBR32E76Z123487").make(make).build();
		
		Cars carUpdate = Cars.builder().id(2L).model("Uno1").years(2017).chassi("1NXBR32E76Z123487").make(make).build();
		
		

		when(carsService.upDateCar(carUpdate,2L)).thenReturn(carUpdate);
		ResponseEntity<Map<String, Object>> response = carsController.upDateCar(2L, carUpdate);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Veículo atualizado com sucesso!", response.getBody().get("message"));
		assertEquals(carUpdate, response.getBody().get("car"));
		
	}
	
	@Test
	void testDeleteCar() {
		
		    Long carId = 5L;

		    doNothing().when(carsService).deleteById(carId);

		    ResponseEntity<Map<String, Object>> response = carsController.deleteCar(carId);

		    assertEquals(HttpStatus.OK, response.getStatusCode());
		    assertEquals("Veículo deletado com sucesso!", response.getBody().get("message"));
		
		
	}

}
