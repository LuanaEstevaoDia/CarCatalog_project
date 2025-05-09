package com.developer.carsCatalog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
		System.out.println("Exceção capturada: " + ex.getMessage());
		Map<String, String> response = new HashMap<>();
		response.put("message", ex.getMessage());
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateKeyException(DuplicateKeyException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(CarNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleCarNotFoundException(CarNotFoundException ex){
		Map<String, String> response = new HashMap<>();
		response.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		
	}

}
