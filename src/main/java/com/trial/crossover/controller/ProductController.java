package com.trial.crossover.controller;

import com.trial.crossover.dto.FieldErrorDTO;
import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Controller
@RequestMapping(value = "products", produces = "application/json;charset=UTF-8")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity all() {
		return new ResponseEntity(productService.all(), HttpStatus.OK);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity create(@RequestBody @Valid ProductDTO dto) {
		return new ResponseEntity(productService.create(dto), HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity update(@RequestBody @Valid ProductDTO dto) {
		//adding simplistic validation just to avoid being negligent
		if (dto.getId() == null) {
			return new ResponseEntity(new FieldErrorDTO("id", "Can't be null"), HttpStatus.BAD_REQUEST);
		}

		dto = productService.update(dto);

		if (dto == null) {
			return new ResponseEntity(new FieldErrorDTO("id", "invalid value"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity get(@PathVariable long id) {
		ProductDTO dto = productService.get(id);

		if (dto == null) {
			return new ResponseEntity(new FieldErrorDTO("id", "invalid value"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(dto, HttpStatus.OK);
	}

}
