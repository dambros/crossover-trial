package com.trial.crossover.service;

import com.trial.crossover.dto.ProductDTO;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public interface ProductService {
	List<ProductDTO> all();
	ProductDTO get(long id);
	ProductDTO create(ProductDTO dto);
	ProductDTO update(ProductDTO dto);
}
