package com.developer.carsCatalog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.services.CarsService;

@RestController
@RequestMapping("/car")

public class CarsController {

	private static final Logger logger = LoggerFactory.getLogger(CarsController.class);

	@Autowired
	CarsService carsService;

	@PostMapping("/save")
	public ResponseEntity<Cars> saveCar(@RequestBody Cars carDetails) {
		Cars  saveCar = carsService.saveCar(carDetails);
		return ResponseEntity.ok(saveCar);

	}

	
	@GetMapping("/all")
	public ResponseEntity<List<Cars>> getAllCars() {
		List<Cars> cars = carsService.findAll();
		return cars.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cars);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Cars> getCarById(@PathVariable Long id) {
		Cars car = carsService.findByIdOrThrow(id);
		return ResponseEntity.ok(car);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Map<String, String>> upDateCar(@PathVariable Long id, @RequestBody Cars carDetails) {
		Map<String, String> message = carsService.upDateCar(carDetails, id);
		return ResponseEntity.ok(message);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteCar(@PathVariable Long id) {
		Map<String, String> response = carsService.deleteById(id);

		return ResponseEntity.ok(response); // Retorna HTTP 200 com confirmação
	}

//
//	@GetMapping("/findByModel")
//	public ResponseEntity<List<Cars>> findByModel(@RequestParam String model) {
//		try {
//			logger.info("Finding cars by model: {}", model);
//			List<Cars> cars = carsService.findByModel(model);
//			logger.info("Found {} cars", cars.size());
//			return new ResponseEntity<>(cars, HttpStatus.OK);
//
//		} catch (Exception e) {
//			logger.error("Error finding cars by model: " + model, e);
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//
//	}
//
//	@GetMapping("/findByMake")
//	public ResponseEntity<List<Cars>> findByMake(@RequestParam Long idMake) {
//		try {
//			logger.info("Finding cars by model: {}", idMake);
//			List<Cars> cars = carsService.findByMake(idMake);
//			logger.info("Found {} cars", cars.size());
//			return new ResponseEntity<>(cars, HttpStatus.OK);
//
//		} catch (Exception e) {
//			logger.error("Error finding cars by model: " + idMake, e);
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//
//	}

//	@GetMapping("/findByYears")
//	public ResponseEntity<List<Cars>> findByCarsYears(@RequestParam int years) {
//		try {
//			logger.info("Finding cars by model: {}", years);
//			List<Cars> cars = carsService.findByCarsYears(years);
//			logger.info("Found {} cars", cars.size());
//			return new ResponseEntity<>(cars, HttpStatus.OK);
//
//		} catch (Exception e) {
//			logger.error("Error finding cars by model: " + years, e);
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//
//	}

}
