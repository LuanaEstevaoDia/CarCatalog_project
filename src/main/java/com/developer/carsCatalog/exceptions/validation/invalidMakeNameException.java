package com.developer.carsCatalog.exceptions.validation;

public class invalidMakeNameException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public invalidMakeNameException (String message) {
		super(message);
	}

}
