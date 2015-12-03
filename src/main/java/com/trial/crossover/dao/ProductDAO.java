package com.trial.crossover.dao;

import com.trial.crossover.model.Product;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface ProductDAO {
	List<Product> all();
	Product get(long id);
	Product create(Product product);
	Product update(Product product);
	void batchUpdate(List<Product> products);
}
