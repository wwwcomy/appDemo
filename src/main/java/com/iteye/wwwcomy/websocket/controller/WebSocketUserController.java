package com.iteye.wwwcomy.websocket.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.iteye.wwwcomy.entity.User;

/**
 * If you call the @SendTo method directly from the backend, seems the client
 * could not be notified. However, things can be done using the
 * SimpMessagingTemplate, it can be automatically autowired.
 * 
 * @author wwwcomy
 *
 */
@Controller
public class WebSocketUserController {
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public User getUser(long userId) throws Exception {
		return new User(userId, "Hello, new user " + userId + "!");
	}

	@SendTo("/topic/greetings")
	public User notifyClient() {
		long randomId = new Random().nextLong();
		System.out.println("Pushing...");
		return new User(randomId, "Hello, pushing user " + randomId + "!");
	}

	public void notifyClient1() {
		long randomId = new Random().nextLong();
		System.out.println("Pushing1...");
		User user = new User(randomId, "Hello, pushing user " + randomId + "!");
		this.template.convertAndSend("/topic/greetings", user);
	}

	@Autowired
	private SimpMessagingTemplate template;

	public SimpMessagingTemplate getTemplate() {
		return template;
	}

	public void setTemplate(SimpMessagingTemplate template) {
		this.template = template;
	}
}
