package com.edgar.askusanything.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

	@GetMapping("/")
	public String redirect() {
		return "redirect:/questions";
	}
	
}
