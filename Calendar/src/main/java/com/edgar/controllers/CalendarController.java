package com.edgar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edgar.calendar.services.CalendarService;

@Controller
public class CalendarController {
	@Autowired
	private CalendarService cService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
}
