package com.iteye.wwwcomy.appdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getValue() {
		return "test";
	}
}
