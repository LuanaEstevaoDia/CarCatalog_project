package com.developer.carsCatalog.utils;

public enum MakeValidationMessage {
	
	INVALID_NAME("O campo nome da marca é obrigatório"),
	MAKE_NOT_FOUND("Marca não encontrada"),
	MAKE_CONFLICT("A marca não pode ser alterada porque já está vinculada a um veículo."),
	CNPJ_INVALID_FORMAT("CNPJ inválido. Deve conter 14 dígitos númericos."),
	DUPLICATE_MAKE_NAME("Já existe uma marca registrada com o nome '%s'. Escolha um nome diferente.");

	
	private final String message;
	
	MakeValidationMessage(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
