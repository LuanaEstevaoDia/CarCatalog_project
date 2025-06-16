package com.developer.carsCatalog.utils;

public enum ItemValidationMessages {
	INVALID_ITEM_NAME("O campo nome do acessório não pode ser vazio ou nulo"),
	INVALID_ITEM_DESCRIPTION("O campo de descrição não pode ser vazio ou nulo");
	
	private final String message;

	ItemValidationMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	

}
