package com.trial.crossover.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Entity
public class CustomerDTO {

	private Long id;
	private String phone1;
	private String phone2;
	private Long currentCredit;

	@NotEmpty(message = "{generic.not.empty}")
	private String name;

	@NotEmpty(message = "{generic.not.empty}")
	private String address;

	@NotNull(message = "{generic.not.null}")
	private Long creditLimit;

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

	public Long getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Long creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Long getCurrentCredit() {
		return currentCredit;
	}

	public void setCurrentCredit(Long currentCredit) {
		this.currentCredit = currentCredit;
	}
}
