package com.developer.carsCatalog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.carsCatalog.entities.Items;
import com.developer.carsCatalog.exceptions.validation.InvalidItemDescriptionException;
import com.developer.carsCatalog.exceptions.validation.InvalidItemNameException;
import com.developer.carsCatalog.repositories.ItemsRepository;
import com.developer.carsCatalog.services.ItemsService;


@RequestMapping("/item")
@RestController
public class ItemsController {
	
	
	@Autowired
	ItemsService itemsService;
	
	
	@Autowired
	ItemsRepository itemsRepository;
	
	
	@PostMapping("/save")
	ResponseEntity<Map<String, Object>> saveItem(@RequestBody Items itemDetails){
		Map<String, Object> response = new HashMap<>();
		
		try {
			Items ItemSaved = itemsService.saveItem(itemDetails);
			response.put("message", "Item salvo com sucesso!");
			response.put("items", ItemSaved);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}catch(InvalidItemNameException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}catch(InvalidItemDescriptionException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}
		
	}
	@GetMapping("/all")
	ResponseEntity<List<Items>> getAllItems(){
		List<Items> item = itemsService.findAll();
		
		return item.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(item);
	}
	
	
	@GetMapping("/{id}")
	ResponseEntity<Items> getItemById(@PathVariable Long id){
	 Items item = itemsService.findByIdOrThrow(id);
		 
		 return ResponseEntity.ok(item);
	}
	
	
	
	@PutMapping("/update/{id}")
	ResponseEntity<Map<String, Object>> updateItem(@RequestBody Items item, @PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			Items updatedItem = itemsService.updateItems(item, id);
			response.put("message", "Item atualizado com sucesso!");
			response.put("item", updatedItem);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}catch(InvalidItemNameException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}catch(InvalidItemDescriptionException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	ResponseEntity<Map<String, Object>> deleteItem(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		itemsService.deleteItemById(id);
		response.put("message", "Item deletado com sucesso!");
		
		return ResponseEntity.ok(response);
		
		
		
		
	}
	

}
