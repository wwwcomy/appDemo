package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	@RequestMapping("/getUserInfo")
	public String getUserInfo() {
		return "resource1";
	}
	
	@RequestMapping("/getUserInfo2")
	public String getUserInfo2() {
		return "resource2";
	}
}
