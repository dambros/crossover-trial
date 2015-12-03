package com.trial.crossover.dto;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public class SalesOrderProductDTO implements DTO {

	private Long id;
	private Integer productQuantity;
	private ProductDTO product;

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
		if (productQuantity != null && productQuantity > 0) {
			this.productQuantity = productQuantity;
		}
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
}
