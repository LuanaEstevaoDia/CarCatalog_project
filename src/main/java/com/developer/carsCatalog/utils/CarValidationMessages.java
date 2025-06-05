package com.developer.carsCatalog.utils;

public enum CarValidationMessages {
	INVALID_MODEL("O campo modelo é obrigatório"),
	INVALID_YEAR("Ano do veículo inválido"),
	INVALID_CHASSI_ERROR("Valor referente a 17 caracteres"),
	INVALID_CHASSI("Chassi existente em outro veículo"),
	INVALID_MAKE("A marca do carro é obrigatória"),
	INVALID_CHANGE_CHASSI("O valor do chassi não pode  ser alterado"),
	CAR_NOT_FOUND("Carro não encontrado com o ID: ");
	
	private final String message;
	
	CarValidationMessages(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	

}
