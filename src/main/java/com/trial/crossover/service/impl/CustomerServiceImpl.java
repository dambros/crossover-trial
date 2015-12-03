package com.trial.crossover.service.impl;

import com.trial.crossover.dao.CustomerDAO;
import com.trial.crossover.dto.CustomerDTO;
import com.trial.crossover.model.Customer;
import com.trial.crossover.service.CustomerService;
import com.trial.crossover.transformer.GenericTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Service
class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private GenericTransformer<CustomerDTO, Customer> transformer;

	@Transactional(readOnly = true)
	public List<CustomerDTO> all() {
		List<Customer> customers = customerDAO.all();
		List<CustomerDTO> dtos = new ArrayList<>(customers.size());

		for (Customer c : customers) {
			dtos.add(transformer.getDTOFromModel(c, CustomerDTO.class));
		}

		return dtos;
	}

	@Transactional(readOnly = true)
	public CustomerDTO get(long id) {
		return transformer.getDTOFromModel(customerDAO.get(id), CustomerDTO.class);
	}

	@Transactional
	public CustomerDTO create(CustomerDTO dto) {
		Customer c = transformer.getModelFromDTO(dto, Customer.class);
		c = customerDAO.create(c);
		return transformer.getDTOFromModel(c, CustomerDTO.class);
	}

	@Transactional
	public CustomerDTO update(CustomerDTO dto) {
		Customer c = transformer.getModelFromDTO(dto, Customer.class);
		c = customerDAO.update(c);
		return transformer.getDTOFromModel(c, CustomerDTO.class);
	}

}
