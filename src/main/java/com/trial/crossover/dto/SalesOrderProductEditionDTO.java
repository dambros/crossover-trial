package com.trial.crossover.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public class SalesOrderProductEditionDTO implements DTO {

	private Long id;

	@NotNull(message = "{generic.not.null}")
	private Long product;

	@NotNull(message = "{generic.not.null}")
	private Integer productQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		if (productQuantity != null && productQuantity < 0) {
			this.productQuantity = 0;
		} else {
			this.productQuantity = productQuantity;
		}
	}

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}
}
