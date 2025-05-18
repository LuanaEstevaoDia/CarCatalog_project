package com.developer.carsCatalog.exceptions.validation;

public class InvalidCarModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidCarModelException(String message) {
		super(message);
	}

}
