package com.trial.crossover.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public class SalesOrderEditionDTO implements DTO {

	private Long id;

	@NotNull(message = "{generic.not.null}")
	private Long orderNumber;

	@NotNull(message = "{generic.not.null}")
	private Float totalPrice;

	@NotNull(message = "{generic.not.null}")
	@Valid
	private List<SalesOrderProductEditionDTO> orderProducts;

	@NotNull(message = "{generic.not.null}")
	private Long customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<SalesOrderProductEditionDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<SalesOrderProductEditionDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}
}
