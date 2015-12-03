package com.trial.crossover.service.impl;

import com.trial.crossover.dao.ProductDAO;
import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.model.Product;
import com.trial.crossover.service.ProductService;
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
class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private GenericTransformer<ProductDTO, Product> transformer;

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> all() {
		List<Product> products = productDAO.all();
		List<ProductDTO> dtos = new ArrayList<>(products.size());

		for (Product p : products) {
			dtos.add(transformer.getDTOFromModel(p, ProductDTO.class));
		}

		return dtos;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO get(long id) {
		Product p = productDAO.get(id);
		if (p == null) {
			return null;
		}
		return transformer.getDTOFromModel(p, ProductDTO.class);
	}

	@Override
	@Transactional
	public ProductDTO create(ProductDTO dto) {
		Product p = transformer.getModelFromDTO(dto, Product.class);
		p = productDAO.create(p);
		return transformer.getDTOFromModel(p, ProductDTO.class);

	}

	@Override
	@Transactional
	public ProductDTO update(ProductDTO dto) {
		Product p = productDAO.get(dto.getId());

		if (p == null) {
			return null;
		}

		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setAvailableQuantity(dto.getAvailableQuantity());
		p = productDAO.update(p);

		return transformer.getDTOFromModel(p, ProductDTO.class);
	}

}
