package com.iteye.wwwcomy.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTimer {
	@Autowired
	public WebSocketUserController controller;

	@Scheduled(cron = "0/5 * * * * ?")
	public void run() {
		controller.notifyClient();
	}

	@Scheduled(cron = "0/5 * * * * ?")
	public void run1() {
		controller.notifyClient1();
	}

	public WebSocketUserController getController() {
		return controller;
	}

	public void setController(WebSocketUserController controller) {
		this.controller = controller;
	}
}
