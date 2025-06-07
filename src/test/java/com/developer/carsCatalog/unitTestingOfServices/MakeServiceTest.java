package com.developer.carsCatalog.unitTestingOfServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.exceptions.MakeConflictException;
import com.developer.carsCatalog.exceptions.validation.InvalidateMakeRegisteredNameException;
import com.developer.carsCatalog.exceptions.validation.InvalidMakeNameException;
import com.developer.carsCatalog.exceptions.validation.InvalidateDuplicateChassisCarException;
import com.developer.carsCatalog.repositories.CarsRepository;
import com.developer.carsCatalog.repositories.MakeRepository;
import com.developer.carsCatalog.services.MakeService;
import com.developer.carsCatalog.utils.MakeValidationMessage;


@ActiveProfiles("test")
public class MakeServiceTest {
	
	@InjectMocks
	MakeService makeService;
	
	
	@Mock
	MakeRepository makeRepository;
	
	@Mock
	CarsRepository carsRepository;

	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		
				
	}

		@Test
		public void shouldCreateMakeSuccessfully() {
		   
		    Make make = Make.builder()
		    		.id(1L)
		            .name("Fiat")
		            .cnpj("45.678.912/0001-31") 
		            .build();

		  
		    when(makeRepository.findByName("Fiat")).thenReturn(Optional.empty());
		    when(makeRepository.save(any(Make.class))).thenReturn(make);
		    when(carsRepository.existsByMakeId(1L)).thenReturn(false);

		   
		    Make savedMake = makeService.findByNameOrCreate(make);

		   
		    assertNotNull(savedMake);
		    assertEquals("Fiat", savedMake.getName());
		    assertEquals("45.678.912/0001-31", savedMake.getCnpj());
		
		 }
		
		@Test
		public void shouldFindMakeById() {
			
			 Make make = Make.builder()
			    		.id(1L)
			    		.name("Fiat")
			            .build();
			 
			 when(makeRepository.findById(make.getId())).thenReturn(Optional.of(make));
			 
			 Make result = makeService.findByIdOrThrow(make.getId());
			 
			 assertNotNull(result);
			 assertEquals(1L, result.getId());
			 assertEquals("Fiat", result.getName());
		}
		
		
		@Test
		public void shouldUpdateMakeSuccessfully() {
			
			 Make make = Make.builder()
			    		.id(1L)
			            .name("Fiat")
			            .cnpj("45.678.912/0001-31") 
			            .build();
			 
			 Make makeUpdate =  Make.builder()
			    		.id(1L)
			            .name("Fiat2025")
			            .cnpj("45.678.912/0001-31") 
			            .build();
			 
			 when(makeRepository.findById(1L)).thenReturn(Optional.of(make));
			 when(makeRepository.save(any(Make.class))).thenReturn(makeUpdate);
			 when(carsRepository.existsByMakeId(1L)).thenReturn(false);
			 
			 
			Make resulUpdate = makeService.updateMake(1L, makeUpdate);
			
			assertNotNull(resulUpdate);
			assertEquals(1L, resulUpdate.getId());
			assertEquals("Fiat2025", resulUpdate.getName());
			assertEquals("45.678.912/0001-31", resulUpdate.getCnpj());
			
			 
			 
			
		}
		
		@Test
		public void shouldThrowExceptionWhenMakeNameIsNull() {
			
			 Make make = Make.builder()
			    		.id(1L)
			            .name(null)
			            .cnpj("45.678.912/0001-31") 
			            .build();
			

				Exception exception = assertThrows(InvalidMakeNameException.class, () -> {
					makeService.findByNameOrCreate(make);

				});
				
				assertEquals(MakeValidationMessage.INVALID_NAME.getMessage(), exception.getMessage());
		}
		
		@Test
		public void shouldThrowExceptionWhenMakeNameIsEmpty() {
			 Make make = Make.builder()
			    		.id(1L)
			            .name("")
			            .cnpj("45.678.912/0001-31") 
			            .build();
			

				Exception exception = assertThrows(InvalidMakeNameException.class, () -> {
					makeService.findByNameOrCreate(make);

				});
				
				assertEquals(MakeValidationMessage.INVALID_NAME.getMessage(), exception.getMessage());
		
		}
		
		  @Test 
		  public void shouldThrowExceptionWhenMakeAlreadyExists() {
		  
		  
		  Make name = Make.builder() 
				  .name("Ford")
				  .cnpj("45.678.912/0001-31") 
				  .build();
		  
		  when(makeRepository.findByName("Ford")).thenReturn(Optional.of(name)); 
		  Exception exception = assertThrows(InvalidateMakeRegisteredNameException.class, () -> {
		   makeService.findByNameOrCreate(name);
		  
		  });
		  
		  assertEquals(MakeValidationMessage.DUPLICATE_MAKE_NAME.getMessage(),
		  exception.getMessage());
		  
		  }
		  
		  @Test
		  
		  public void shouldThrowExceptionWhenMakeIsLinkedToCar() {
			  
			 
			 Make makeDelete =  Make.builder()
			    		.id(1L)
			    		.name("Ford")
						.cnpj("45.678.912/0001-31") 
			            .build();
			 
			when(carsRepository.existsByMakeId(1L)).thenReturn(true);
			Exception exception = assertThrows(MakeConflictException.class, () -> {
			makeService.deleteOrUpdateMakeLinkedToCar(makeDelete.getId());
				 
			 });
			assertEquals(MakeValidationMessage.MAKE_CONFLICT.getMessage(), exception.getMessage());
			
			  
		  }
		 
			
		

}
