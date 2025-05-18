package com.developer.carsCatalog.exceptions.validation;

public class InvalidCarMakeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidCarMakeException(String message) {
		super(message);
	}

}
