package com.trial.crossover.dao;

import com.trial.crossover.model.Customer;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface SalesOrderDAO {
	List<Customer> all();
	Customer get(long id);
	Customer create(Customer Customer);
	Customer update(Customer customer);
}
