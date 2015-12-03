package com.trial.crossover.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Entity
@Table(name = "sales_orders")
public class SalesOrder implements com.trial.crossover.dto.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sales_order_id")
	private Long id;

	@Column(name = "sales_order_number")
	private long orderNumber;

	@Column(name = "sales_order_total_price")
	private float totalPrice;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "order_products", joinColumns = {
			@JoinColumn(name = "order_id", nullable = false)}, inverseJoinColumns = {
			@JoinColumn(name = "order_product_id", nullable = false)
	})
	private List<SalesOrderProduct> orderProducts;

	@ManyToOne
	@JoinColumn(name = "sales_order_customer_id")
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<SalesOrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<SalesOrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
