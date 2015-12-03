package com.trial.crossover.dao.impl;

import com.trial.crossover.dao.BaseDAO;
import com.trial.crossover.dao.SalesOrderDAO;
import com.trial.crossover.model.Product;
import com.trial.crossover.model.SalesOrder;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Repository
class SalesOrderDAOImpl extends BaseDAO implements SalesOrderDAO {

	@Override
	public List<SalesOrder> all() {
		Query query = getCurrentSession().createQuery("FROM SalesOrder s LEFT JOIN FETCH s.orderProducts p ORDER BY s.id ASC");
		return (List<SalesOrder>) query.list();
	}

	@Override
	public SalesOrder get(long id) {
		Query query = getCurrentSession().createQuery("FROM SalesOrder s LEFT JOIN FETCH s.orderProducts p WHERE s.id = :id");
		query.setParameter("id", id);
		return (SalesOrder) query.uniqueResult();
	}

	@Override
	public SalesOrder create(SalesOrder salesOrder) {
		getCurrentSession().save(salesOrder);
		return salesOrder;
	}

	@Override
	public SalesOrder update(SalesOrder salesOrder) {
		getCurrentSession().update(salesOrder);
		return salesOrder;
	}

	@Override
	public void delete(long id) {
		getCurrentSession().delete(get(id));
	}
}
