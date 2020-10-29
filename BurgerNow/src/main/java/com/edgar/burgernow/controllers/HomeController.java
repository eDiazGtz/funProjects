package com.edgar.burgernow.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edgar.burgernow.models.User;
import com.edgar.burgernow.services.UserService;
import com.edgar.burgernow.validatiors.UserValidator;



@Controller
public class HomeController {
	@Autowired
	private UserService uService;
	@Autowired
	private UserValidator validator;

	
	//Login
	@GetMapping("/")
	public String landing(@ModelAttribute("user") User user) {
		return "landing.jsp";
	}
	
	//Register User
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		validator.validate(user, result);
		if(result.hasErrors()) {
			//If validation errors send them back to Login Page
			return "landing.jsp";
		}
		User newUser = this.uService.registerUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/";
	}

	//Login to Session
	@PostMapping("/login")
	public String login(HttpSession session, @RequestParam("lemail") String email, @RequestParam("lpassword") String password, RedirectAttributes redirectAttrs, Model viewModel) {
		if(!this.uService.authenticateUser(email, password)) {
			redirectAttrs.addFlashAttribute("loginError", "Invalid Credentials");
			return "redirect:/";
		}
		User user = this.uService.getUserByEmail(email);
		session.setAttribute("user_id", user.getId());
		return "redirect:/dashboard";
	}
	
	//Dashboard
	@GetMapping("/dashboard")
	public String index(@ModelAttribute("event") Event event, HttpSession session, Model viewModel) {
		Long userId = (Long)session.getAttribute("user_id");
		if(userId == null) {
			return "redirect:/";
		}
		User user = this.uService.getOneUser(userId);
		String userState = user.getState();
		viewModel.addAttribute("inState", this.eService.findEventsByState(userState));
		viewModel.addAttribute("notInState", this.eService.findEventsByNotState(userState));
		viewModel.addAttribute("user_id", userId);
		viewModel.addAttribute("user", this.uService.getOneUser(userId));
		return "dashboard.jsp";
	}
	
	//Joining (Attending) an Event
	@GetMapping("/join/{id}")
	private String like(@PathVariable("id") Long id, HttpSession session){
		Long userId = (Long)session.getAttribute("user_id");
		Long eventId = id;
		User attendee = this.uService.getOneUser(userId);
		Event attendedEvent = this.eService.findOneEvent(eventId);
		this.eService.addAttendee(attendee, attendedEvent);
		return "redirect:/dashboard";
	}

	//Canceling (Unjoinin) an Event
	@GetMapping("/unjoin/{id}")
	private String unlike(@PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long)session.getAttribute("user_id");
		Long eventId = id;
		User attendee = this.uService.getOneUser(userId);
		Event attendedEvent = this.eService.findOneEvent(eventId);
		this.eService.removeAttendee(attendee, attendedEvent);
		return "redirect:/dashboard";
	}
	
	
	//Creating new Event
	@PostMapping("/events/new")
	public String newEvent(@Valid @ModelAttribute("event") Event newEvent, BindingResult result, Model viewModel, HttpSession session) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		if(result.hasErrors()) {
			Long userId = (Long)session.getAttribute("user_id");
			viewModel.addAttribute("user_id", userId);
			return "edit.jsp";
		}
		eService.saveEvent(newEvent);
		return "redirect:/dashboard";
	}

	
	
	
	
	//Editing Event
	@GetMapping("/events/{id}/edit")
	public String viewEditEvent(@ModelAttribute("event") Event event, @PathVariable("id") Long id, HttpSession session, Model viewModel) {
		Long userId = (Long)session.getAttribute("user_id");
		viewModel.addAttribute("user_id", userId);
		if(!eService.findHostId(id).equals(session.getAttribute("user_id"))) {
			return "redirect:/dashboard";
		}
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		viewModel.addAttribute("event", this.eService.findOneEvent(id));
		return "edit.jsp";
	}

	//Editing Event Logic
	@PostMapping("/events/{id}/edit")
	public String editEvent(@Valid @ModelAttribute("event") Event updatedEvent, BindingResult result, Model viewModel, HttpSession session, @PathVariable("id") Long id) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		if(result.hasErrors()) {
			Long userId = (Long)session.getAttribute("user_id");
			viewModel.addAttribute("user_id", userId);
			return "edit.jsp";
		}
		this.eService.saveEvent(updatedEvent);
		return "redirect:/dashboard";
	}

	
	
	//View Event
	@GetMapping("/events/{id}")
	public String viewEvent(@ModelAttribute("message") Message message, @PathVariable("id") Long id, Model viewModel, HttpSession session) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		viewModel.addAttribute("event", this.eService.findOneEvent(id));
		viewModel.addAttribute("hostName", this.eService.findHostName(id));
		return "showEvent.jsp";
	}
	
	//Add Message Logic
	@PostMapping("/message/new")
	public String newMessage(@Valid @ModelAttribute("message") Message message, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "showEvent.jsp";
		}
		Message newMessage = this.mService.createMessage(message);
		return "redirect:/events/" + newMessage.getEvent().getId();
	}
	
	
	//LogOut
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	//Delete
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.eService.deleteEvent(id);
		return "redirect:/dashboard";
	}
	
	
}
