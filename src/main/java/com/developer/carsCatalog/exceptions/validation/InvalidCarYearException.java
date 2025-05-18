package com.developer.carsCatalog.exceptions.validation;

public class InvalidCarYearException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidCarYearException(String message) {
		super(message);
	}
	

}
