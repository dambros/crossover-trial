package com.trial.crossover.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Entity
@Table(name = "customers")
public class Customer implements com.trial.crossover.dto.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private Long id;

	@Column(name = "customer_name", nullable = false)
	private String name;

	@Column(name = "customer_address", nullable = false)
	private String address;

	@Column(name = "customer_phone1")
	private String phone1;

	@Column(name = "customer_phone2")
	private String phone2;

	@Column(name = "customer_credit_limit", nullable = false)
	private float creditLimit;

	@Column(name = "customer_current_credit")
	private float currentCredit;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<SalesOrder> orders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public float getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(float creditLimit) {
		this.creditLimit = creditLimit;
	}

	public float getCurrentCredit() {
		return currentCredit;
	}

	public void setCurrentCredit(float currentCredit) {
		this.currentCredit = currentCredit;
	}

	public List<SalesOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<SalesOrder> orders) {
		this.orders = orders;
	}
}
