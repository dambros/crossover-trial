package com.trial.crossover.service.impl;

import com.trial.crossover.dao.ProductDAO;
import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.model.Product;
import com.trial.crossover.service.ProductService;
import com.trial.crossover.transformer.ProductTransformer;
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
class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Transactional(readOnly = true)
	public List<ProductDTO> getProducts() {
		List<Product> products = productDAO.getProducts();
		List<ProductDTO> dtos = new ArrayList<ProductDTO>(products.size());

		for (Product p : products) {
			dtos.add(ProductTransformer.getProductDTOFromModel(p));
		}

		return dtos;
	}

}
