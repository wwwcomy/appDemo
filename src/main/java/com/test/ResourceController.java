package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pic")
public class ResourceController {
	@RequestMapping("/getUserInfo")
	public String getUserInfo() {
		return "resource";
	}
}
