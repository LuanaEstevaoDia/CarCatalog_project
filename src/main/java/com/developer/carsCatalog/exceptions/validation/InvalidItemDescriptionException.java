package com.developer.carsCatalog.exceptions.validation;

public class InvalidItemDescriptionException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public InvalidItemDescriptionException(String message) {
		super(message);
	}

}
