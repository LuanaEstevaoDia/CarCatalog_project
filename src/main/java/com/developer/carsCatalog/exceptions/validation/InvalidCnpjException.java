package com.developer.carsCatalog.exceptions.validation;

public class InvalidCnpjException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidCnpjException(String message) {
		super(message);
	}

}
