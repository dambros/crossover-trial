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
	private Integer quantity;

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
		if (price != null && price > 0) {
			this.price = price;
		}
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		if (quantity != null && quantity > 0) {
			this.quantity = quantity;
		}
	}
}
