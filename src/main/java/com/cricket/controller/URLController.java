package com.cricket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class URLController {
	@GetMapping("/")
	public String indexPage() {
		return "index";
	}
	@GetMapping("/MyProfile")
	public String profilePage() {
		return "profile.html";
	}
	@GetMapping("/login")
	public String loginPage() {
		return "login.html";
	}
	@GetMapping("/SignUp")
	public String signUp() {
		return "sign.html";
	}
	@GetMapping("/search")
	public String search() {
		return "search.html";
	}
	@GetMapping("/MyMatches")
	public String dashboard() {
		return "dashboard.html";
	}
}
