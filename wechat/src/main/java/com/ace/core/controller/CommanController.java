package com.ace.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommanController {
	
	@RequestMapping("/toLogin.do")
	public String loginAdmin(){
		return "login";
	}
	
	@RequestMapping(value={"/","/index.do"})
	public String index(ModelMap model){

		return "index";
	}
	
	@RequestMapping("/welcome.do")
	public String welcome(){
		return "welcome";
	}
	
}
