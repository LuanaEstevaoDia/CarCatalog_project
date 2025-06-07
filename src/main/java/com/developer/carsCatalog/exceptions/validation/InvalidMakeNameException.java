package com.developer.carsCatalog.exceptions.validation;

public class InvalidMakeNameException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidMakeNameException (String message) {
		super(message);
	}

}
