package com.developer.carsCatalog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.exceptions.InvalidDataException;
import com.developer.carsCatalog.exceptions.MakeNotFoundException;
import com.developer.carsCatalog.services.MakeService;

@RequestMapping("/make")
@RestController

public class MakeController {
	
	@Autowired
	private MakeService makeService;
	
	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>>saveMake(@RequestBody Make make){
		Map<String, Object> response = new HashMap<>();
		
		try {
			Make savedMake = makeService.findByNameOrCreate(make);
			response.put("message", "Marca registrada com sucesso");
			response.put("make", savedMake);
			return ResponseEntity.ok(response);
			
		}catch(InvalidDataException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Make>> getAllMake() {
		List<Make> makes = makeService.findAll();
		return makes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(makes);

		//obter todas as marcas
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Make> getMakeById(@PathVariable Long id){
		//Obter marca pelo id
		Make make = makeService.findByIdOrThrow(id);
		return ResponseEntity.ok(make);
	
	

	}
	@PutMapping("/update/{id}")
	public ResponseEntity<Map<String, Object>> updateMake(@PathVariable Long id,  @RequestBody Make updatedMake){
		Map<String, Object> response = new HashMap<>();
		
		try {
			Make makeUpdate = makeService.updateMake(id, updatedMake);
			response.put("message", "Marca atualizada com sucesso!");
			response.put("make", makeUpdate );
			
			return ResponseEntity.ok(response);
			
		}catch(MakeNotFoundException e){
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			
			
		}catch(IllegalStateException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}
		
		
		
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteCar(@PathVariable Long id) {
		makeService.delete(id);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Marca deletada com sucesso!");
		return ResponseEntity.ok(response); // Retorna HTTP 200 com confirmação
	
	}
	

}
