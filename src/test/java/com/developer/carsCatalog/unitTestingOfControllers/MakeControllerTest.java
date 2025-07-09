package com.developer.carsCatalog.unitTestingOfControllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

import com.developer.carsCatalog.controllers.MakeController;
import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.services.MakeService;

@ActiveProfiles("test")
public class MakeControllerTest {
	
	@InjectMocks
	MakeController makeController;
	
	@Mock
	MakeService makeService;
	
	@BeforeEach
	void setUp() {
		
		MockitoAnnotations.openMocks(this);
		
	}
	
	@Test
	void saveSuccessTest() {
		
		Make makeResponse = Make.builder()
				.name("Fiat")
				.cnpj("45.678.912/0001-31")
				.build();
		Make makeRequest = Make.builder()
				.id(1L)
				.name("Fiat")
				.cnpj("45.678.912/0001-31")
				.build();
		
		when(makeService.findByNameOrCreate(makeRequest)).thenReturn(makeResponse);
		ResponseEntity<Map<String, Object>> response = makeController.saveMake(makeRequest);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Marca registrada com sucesso", response.getBody().get("message"));
		assertEquals(makeResponse, response.getBody().get("make"));
		
		
		
	}
	@Test
	void testToGetListAllMakes() {
		Make getMake =  Make.builder()
				.id(1L)
				.name("Fiat")
				.cnpj("45.678.912/0001-31")
				.build();
		List<Make> makeListResponse = List.of(getMake);
		
		when(makeService.findAll()).thenReturn(makeListResponse);
		ResponseEntity<List<Make>> response = makeController.getAllMake();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
		assertEquals("Fiat", response.getBody().get(0).getName());
		
		
	}
	
	@Test
	 void testToGetIdMake() {
		
		Make getIdMake =  Make.builder()
				.id(1L)
				.name("Fiat")
				.cnpj("45.678.912/0001-31")
				.build();
		
		
		when(makeService.findByIdOrThrow(1L)).thenReturn(getIdMake);
		ResponseEntity<Make> response = makeController.getMakeById(1L);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getId());
		
		 
	 }
	
	@Test
	 void testToUpdateIdCar() {
		

		Make make = Make.builder()
				.id(1L)
				.name("Fiat")
				.cnpj("45.678.912/0001-31")
				.build();
		Make makeUpdate = Make.builder()
				.id(1L)
				.name("FiatAtualizado")
				.cnpj("45.678.912/0001-31")
				.build();
		
		when(makeService.updateMake(1L, makeUpdate)).thenReturn(makeUpdate);
		ResponseEntity<Map<String, Object>> response = makeController.updateMake(1L, makeUpdate);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Marca atualizada com sucesso!", response.getBody().get("message"));
		assertEquals(makeUpdate, response.getBody().get("make"));
		 
	 }
	

}
