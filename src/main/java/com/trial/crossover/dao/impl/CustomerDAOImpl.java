package com.trial.crossover.dao.impl;

import com.trial.crossover.dao.BaseDAO;
import com.trial.crossover.dao.ProductDAO;
import com.trial.crossover.model.Product;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Repository
class CustomerDAOImpl extends BaseDAO implements ProductDAO {

	public List<Product> all() {
		Query query = getCurrentSession().createQuery("FROM Product c");
		return (List<Product>) query.list();
	}

	public Product create(Product product) {
		getCurrentSession().save(product);
		return product;
	}
}
