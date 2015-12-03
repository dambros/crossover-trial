package com.trial.crossover.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public class ProductDTO implements DTO {

	private Long id;

	@NotEmpty(message = "{generic.not.empty}")
	private String description;

	@NotNull(message = "{generic.not.null}")
	private Float price;

	@NotNull(message = "{generic.not.null}")
	private Integer availableQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		if (price != null && price < 0) {
			this.price = 0f;
		} else {
			this.price = price;
		}
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		if (availableQuantity != null && availableQuantity < 0) {
			this.availableQuantity = 0;
		} else {
			this.availableQuantity = availableQuantity;
		}
	}

}
