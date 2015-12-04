package com.trial.crossover.controller;

import com.trial.crossover.dto.FieldErrorDTO;
import com.trial.crossover.dto.SalesOrderEditionDTO;
import com.trial.crossover.dto.SalesOrderDTO;
import com.trial.crossover.service.SalesOrderService;
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
import java.util.ArrayList;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@Controller
@RequestMapping(value = "orders", produces = "application/json;charset=UTF-8")
public class SalesOrderController {

	@Autowired
	private SalesOrderService salesOrderService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity all() {
		return new ResponseEntity(salesOrderService.all(), HttpStatus.OK);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity create(@RequestBody @Valid SalesOrderEditionDTO dto) {
		Object obj = salesOrderService.create(dto);

		if (obj instanceof ArrayList) {
			return new ResponseEntity(obj, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity update(@RequestBody @Valid SalesOrderEditionDTO dto) {
		//adding simplistic validation just to avoid being negligent
		if (dto.getId() == null) {
			return new ResponseEntity(new FieldErrorDTO("id", "Can't be null"), HttpStatus.BAD_REQUEST);
		}

		Object obj = salesOrderService.update(dto);

		if (obj instanceof ArrayList) {
			return new ResponseEntity(obj, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity get(@PathVariable long id) {
		SalesOrderDTO dto = salesOrderService.get(id);

		if (dto == null) {
			return new ResponseEntity(new FieldErrorDTO("id", "invalid value"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity delete(@PathVariable long id) {
		salesOrderService.delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
