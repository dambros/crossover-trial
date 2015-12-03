package com.trial.crossover.controller;

import com.trial.crossover.dto.CustomerDTO;
import com.trial.crossover.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Controller
@RequestMapping(value = "customers", produces = "application/json;charset=UTF-8")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomerDTO> all() {
		return customerService.all();
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public CustomerDTO create(@RequestBody @Valid CustomerDTO dto) {
		return customerService.create(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CustomerDTO get(@PathVariable long id) {
		return customerService.get(id);
	}
}
