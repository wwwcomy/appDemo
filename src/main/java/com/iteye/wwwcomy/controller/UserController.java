package com.iteye.wwwcomy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iteye.wwwcomy.entity.User;
import com.iteye.wwwcomy.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	public UserController() {
		System.out.println("Initing...");
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
	public User getUser(@PathVariable long userId) {
		return userService.getUser(userId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
