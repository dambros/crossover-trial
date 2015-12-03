package com.trial.crossover.service;

import com.trial.crossover.dto.CustomerDTO;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface SalesOrderService {
	List<CustomerDTO> all();
	CustomerDTO get(long id);
	CustomerDTO create(CustomerDTO dto);
	CustomerDTO update(CustomerDTO dto);
}
