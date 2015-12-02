package com.trial.crossover.dto;

import javax.persistence.Entity;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Entity
public class CustomerDTO {

	private Long id;
	private String name;
	private String address;
	private String phone1;
	private String phone2;
	private long creditLimit;
	private long currentCredit;

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

	public long getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(long creditLimit) {
		this.creditLimit = creditLimit;
	}

	public long getCurrentCredit() {
		return currentCredit;
	}

	public void setCurrentCredit(long currentCredit) {
		this.currentCredit = currentCredit;
	}
}
