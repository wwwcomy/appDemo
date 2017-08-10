package com.iteye.wwwcomy.service;

import java.util.List;

import com.iteye.wwwcomy.entity.User;

public interface UserService {
	public User getUser(long id);

	public List<User> getUsers();
}
