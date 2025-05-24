package com.developer.carsCatalog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.developer.carsCatalog.exceptions.validation.InvalidCarChassiException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarMakeException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarModelException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarYearException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	public ResponseEntity<Map<String, String>> buildResponse(Exception ex, HttpStatus status){
		Map<String, String> response = new HashMap<>();
		response.put("error: ", ex.getMessage());
		return ResponseEntity.status(status).body(response);
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
		return buildResponse(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateKeyException(DuplicateKeyException ex) {
		return buildResponse(ex, HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(CarNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleCarNotFoundException(CarNotFoundException ex){
		return buildResponse(ex, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MakeNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleMakeNotFoundException(MakeNotFoundException ex){
		return buildResponse(ex, HttpStatus.NOT_FOUND);
		
	}
	

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Map<String, String>> handleInvalidDataException(InvalidDataException ex){
		return buildResponse(ex, HttpStatus.BAD_REQUEST);
		
	}
	

	@ExceptionHandler(InvalidCarModelException.class)
	public ResponseEntity<Map<String, String>> handleInvalidCarModelException(InvalidCarModelException ex){
		return buildResponse(ex, HttpStatus.BAD_REQUEST);
		
	}
	

	@ExceptionHandler(InvalidCarYearException.class)
	public ResponseEntity<Map<String, String>> handleInvalidCarYearException(InvalidCarYearException ex){
		return buildResponse(ex, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(InvalidCarChassiException.class)
	public ResponseEntity<Map<String, String>> handleInvalidCarChassiException(InvalidCarChassiException ex){
		return buildResponse(ex, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(InvalidCarMakeException.class)
	public ResponseEntity<Map<String, String>> handleInvalidCarMakeException(InvalidCarMakeException ex){
		return buildResponse(ex, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleItemNotFoundException(ItemNotFoundException ex){
		return buildResponse(ex, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler( BadRequestException.class)
	public ResponseEntity<Map<String, String>> handleBadRequestException( BadRequestException ex){
		return buildResponse(ex, HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
	

}
