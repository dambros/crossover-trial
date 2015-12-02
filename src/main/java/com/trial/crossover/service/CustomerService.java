package com.trial.crossover.service;

import com.trial.crossover.dto.ProductDTO;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface CustomerService {
	List<ProductDTO> all();
	ProductDTO create(ProductDTO dto);
}
