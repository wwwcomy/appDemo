package com.iteye.wwwcomy.appdemo.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@GetMapping("")
	public Principal user(Principal user) {
		return user;
	}
}
