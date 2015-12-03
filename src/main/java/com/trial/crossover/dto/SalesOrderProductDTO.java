package com.trial.crossover.dto;

import com.trial.crossover.model.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Entity
@Table(name = "sales_orders_products")
public class SalesOrderProductDTO implements com.trial.crossover.dto.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sales_orders_products_id")
	private Long id;

	@Column(name = "sales_orders_products_quantity")
	private int productQuantity;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
