package com.developer.carsCatalog.exceptions.validation;

public class InvalidItemNameException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidItemNameException(String message) {
		super(message);
		
	}
}
