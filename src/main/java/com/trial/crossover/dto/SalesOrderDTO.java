package com.trial.crossover.dto;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public class SalesOrderDTO implements DTO {

	private Long id;
	private Long orderNumber;
	private Float totalPrice;
	private List<SalesOrderProductDTO> orderProducts;
	private CustomerDTO customer;

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

	public List<SalesOrderProductDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<SalesOrderProductDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
}
