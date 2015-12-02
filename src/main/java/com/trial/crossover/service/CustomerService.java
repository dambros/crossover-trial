package com.trial.crossover.service;

import com.trial.crossover.dto.CustomerDTO;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface CustomerService {
	List<CustomerDTO> all();
	CustomerDTO get(long id);
	CustomerDTO create(CustomerDTO dto);
}
