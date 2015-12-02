package com.trial.crossover.dao;

import com.trial.crossover.model.Product;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface CustomerDAO {
	List<Product> all();
	Product create(Product product);
}
