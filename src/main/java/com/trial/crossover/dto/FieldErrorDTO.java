package com.trial.crossover.dto;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public class FieldErrorDTO {

	private String field;

	private String message;

	public FieldErrorDTO(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

}
