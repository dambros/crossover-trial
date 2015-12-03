package com.trial.crossover.dao.impl;

import com.trial.crossover.dao.BaseDAO;
import com.trial.crossover.dao.CustomerDAO;
import com.trial.crossover.dto.CustomerDTO;
import com.trial.crossover.model.Customer;
import com.trial.crossover.model.Product;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Repository
class CustomerDAOImpl extends BaseDAO implements CustomerDAO {

	public List<Customer> all() {
		Query query = getCurrentSession().createQuery("FROM Customer c");
		return (List<Customer>) query.list();
	}

	public Customer get(long id) {
		return (Customer) getCurrentSession().get(Customer.class, id);
	}

	public Customer create(Customer customer) {
		getCurrentSession().save(customer);
		return customer;
	}
}
