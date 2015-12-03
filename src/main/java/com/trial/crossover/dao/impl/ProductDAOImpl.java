package com.trial.crossover.dao.impl;

import com.trial.crossover.dao.BaseDAO;
import com.trial.crossover.dao.ProductDAO;
import com.trial.crossover.model.Product;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Repository
class ProductDAOImpl extends BaseDAO implements ProductDAO {

	public List<Product> all() {
		Query query = getCurrentSession().createQuery("FROM Product c");
		return (List<Product>) query.list();
	}

	public Product get(long id) {
		return (Product) getCurrentSession().get(Product.class, id);
	}

	public Product create(Product product) {
		getCurrentSession().save(product);
		return product;
	}

	public Product update(Product product) {
		getCurrentSession().update(product);
		return product;
	}
}
