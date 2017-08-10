package com.iteye.wwwcomy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.iteye.wwwcomy.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public User getUser(long id) {
		return new User(id, "Dummy User");
	}

	@Override
	public List<User> getUsers() {
		List<User> list = new ArrayList<>();
		list.add(new User(1, "Dummy User 1"));
		list.add(new User(2, "Dummy User 2"));
		return list;
	}

}
