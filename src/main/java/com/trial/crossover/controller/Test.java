package com.trial.crossover.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by: dambros
 * Date: 12/1/2015
 */
@Controller
@RequestMapping(value = "test", produces = "application/json;charset=UTF-8")
public class Test {

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String getTest() {
		return "hello world";
	}
}
