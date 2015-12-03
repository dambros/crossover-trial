package com.trial.crossover.dao;

import com.trial.crossover.model.SalesOrder;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface SalesOrderDAO {
	List<SalesOrder> all();
	SalesOrder get(long id);
	SalesOrder create(SalesOrder salesOrder);
	SalesOrder update(SalesOrder salesOrder);
	void delete(long id);
}
