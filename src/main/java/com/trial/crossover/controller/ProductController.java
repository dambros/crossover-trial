package com.trial.crossover.controller;

import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Controller
@RequestMapping(value = "product", produces = "application/json;charset=UTF-8")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductDTO> all() {
		return productService.all();
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public ProductDTO create(@RequestBody ProductDTO dto) {
		return productService.create(dto);
	}
}
