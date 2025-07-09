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

import com.developer.carsCatalog.entities.Items;
import com.developer.carsCatalog.exceptions.validation.InvalidItemNameException;
import com.developer.carsCatalog.repositories.ItemsRepository;
import com.developer.carsCatalog.services.ItemsService;
import com.developer.carsCatalog.utils.ItemValidationMessages;


@ActiveProfiles("test")
public class ItemsServiceTest {
	
	@InjectMocks
	ItemsService itemsService;
	
	@Mock
	ItemsRepository itemsRepository;
	
	@BeforeEach
	void setup() {
		
		MockitoAnnotations.openMocks(this);
		
	}
	
	@Test
	public void saveItemSucessFully() {
		
		Items items = Items.builder()
				.name("AirBag")
				.build();
		
			when(itemsRepository.findByName("AirBag")).thenReturn(Optional.empty());
		    when(itemsRepository.save(any(Items.class))).thenReturn(items);
		    

		   
		    Items savedItem = itemsService.saveItem(items);

		   
		    assertNotNull(savedItem);
		    assertEquals("AirBag", savedItem.getName());
		   
		
	}
	
	@Test
	public void updateItemsSucessFully() {
		
		Items items = Items.builder()
				.id(1L)
				.name("AirBag")
				.build();
		
		Items updateItems = items.builder()
				.id(1L)
				.name("AirBag")
				.build();
		
		 when(itemsRepository.findById(1L)).thenReturn(Optional.of(items));
		 when(itemsRepository.save(any(Items.class))).thenReturn(updateItems);
		
		 
		 
		Items resulUpdate = itemsService.updateItems(updateItems, 1L);
		
		assertNotNull(resulUpdate);
		assertEquals(1L, resulUpdate.getId());
		assertEquals("AirBag", resulUpdate.getName());
	
		
	}
	
	@Test
	public void shouldThrowExceptionWhenItemNameIsNull() {
		
		 Items items = Items.builder()
		    		.id(1L)
		            .name(null)
		            .build();
		

			Exception exception = assertThrows(InvalidItemNameException.class, () -> {
				itemsService.saveItem(items);

			});
			
			assertEquals(ItemValidationMessages.INVALID_ITEM_NAME.getMessage(), exception.getMessage());
	}
	
	@Test
	public void shouldThrowExceptionWhenItemNameIsEmpty() {
		
		 Items items = Items.builder()
		    	   .id(1L)
		           .name("")
		           .build();
		

			Exception exception = assertThrows(InvalidItemNameException.class, () -> {
				itemsService.saveItem(items);

			});
			
			assertEquals(ItemValidationMessages.INVALID_ITEM_NAME.getMessage(), exception.getMessage());
	}
	
	
	
	

}
