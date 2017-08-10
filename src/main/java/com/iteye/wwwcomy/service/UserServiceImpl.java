package com.iteye.wwwcomy.service;

import org.springframework.stereotype.Service;

import com.iteye.wwwcomy.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public User getUser(long id) {
		return new User(id, "Dummy User");
	}

}
