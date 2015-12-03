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

	@Override
	public List<Product> all() {
		Query query = getCurrentSession().createQuery("FROM Product");
		return (List<Product>) query.list();
	}

	@Override
	public Product get(long id) {
		return (Product) getCurrentSession().get(Product.class, id);
	}

	@Override
	public Product create(Product product) {
		getCurrentSession().save(product);
		return product;
	}

	@Override
	public Product update(Product product) {
		getCurrentSession().update(product);
		return product;
	}

	public void batchUpdate(List<Product> products) {
		getCurrentSession().update(products);
	}
}
