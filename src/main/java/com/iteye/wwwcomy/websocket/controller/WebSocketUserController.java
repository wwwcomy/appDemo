package com.iteye.wwwcomy.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.iteye.wwwcomy.entity.User;

@Controller
public class WebSocketUserController {
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public User getUser(long userId) throws Exception {
		return new User(userId, "Hello, new user +" + userId + "!");
	}
}
