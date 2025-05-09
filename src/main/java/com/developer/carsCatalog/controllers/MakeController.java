package com.developer.carsCatalog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.developer.carsCatalog.services.MakeService;

@RequestMapping("/make")
@RestController

public class MakeController {
	
	@Autowired
	private MakeService makeService;
	
	@PostMapping("/save")
	public ResponseEntity<Make> saveMake(@RequestBody Make make){
		return ResponseEntity.ok(makeService.findByNameOrCreate(make));
		//criar uma nova marca
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Make>> getAllCars() {
		List<Make> makes = makeService.findAll();
		return makes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(makes);

		//obter todas as marcas
		
	}
	@GetMapping("/update/{id}")
	public ResponseEntity<Make> getMakeById(@PathVariable Long id){
		//Obter marca pelo id
		Make make = makeService.findByIdOrThrow(id);
		return ResponseEntity.ok(make);
	
	

	}
	@PutMapping("/update/{id}")
	public ResponseEntity<Make> updateMake(@PathVariable Long id,  @RequestBody Make updatedMake){
		//Atualizar marca existente
		Make make = makeService.updateMake(id, updatedMake);
		return ResponseEntity.ok(make);
		
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteCar(@PathVariable Long id) {
		Map<String, String> response = makeService.delete(id);
	
		return ResponseEntity.ok(response); // Retorna HTTP 200 com confirmação
	
	}
	

}
