package com.trial.crossover.transformer;

import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.model.Product;
import org.springframework.beans.BeanUtils;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public abstract class ProductTransformer {

	public static Product getProductFromDTO(ProductDTO dto) {
		Product product = new Product();
		BeanUtils.copyProperties(dto, product);
		return product;
	}

	public static ProductDTO getProductDTOFromModel(Product product) {
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(product, dto);
		return dto;
	}

}
