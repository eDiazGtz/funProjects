package com.edgar.burgernow.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edgar.burgernow.models.Burger;
import com.edgar.burgernow.models.Fry;
import com.edgar.burgernow.models.Order;
import com.edgar.burgernow.models.User;
import com.edgar.burgernow.services.BurgerService;
import com.edgar.burgernow.services.FryService;
import com.edgar.burgernow.services.OrderService;
import com.edgar.burgernow.services.UserService;
import com.edgar.burgernow.validatiors.UserValidator;

@Controller
public class HomeController {
	@Autowired
	private UserService uService;
	@Autowired
	private UserValidator validator;
	@Autowired
	private OrderService oService;
	@Autowired
	private BurgerService bService;
	@Autowired
	private FryService fService;

	

	//Home Page (Root)
	@GetMapping("/")
	public String index(HttpSession session, Model viewModel) {
		Long userId = (Long)session.getAttribute("user_id");
		if(userId == null) {
			return "home.jsp";
		}
		viewModel.addAttribute("user_id", userId);
		viewModel.addAttribute("user", this.uService.getOneUser(userId));
		return "home.jsp";
	}

	
	
	
	//Login
	@GetMapping("/login")
	public String loginPage(@ModelAttribute("user") User user) {
		return "login.jsp";
	}

	//Login to Session
	@PostMapping("/login")
	public String login(HttpSession session, @RequestParam("lemail") String email, @RequestParam("lpassword") String password, RedirectAttributes redirectAttrs, Model viewModel) {
		if(!this.uService.authenticateUser(email, password)) {
			redirectAttrs.addFlashAttribute("loginError", "Invalid Credentials");
			return "redirect:/login";
		}
		User user = this.uService.getUserByEmail(email);
		session.setAttribute("user_id", user.getId());
		return "redirect:/";
	}

	

	//Register Page
	@GetMapping("/register")
	public String registerPage(@ModelAttribute("user") User user) {
		return "register.jsp";
	}
	
	//Register User
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		validator.validate(user, result);
		if(result.hasErrors()) {
			//If validation errors send them back to Register Page
			return "register.jsp";
		}
		User newUser = this.uService.registerUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/";
	}
	
	

	//-----------------------------------
	//ORDERING BURGERS
	//-----------------------------------
	@GetMapping("/burger")
	public String newOrderBurgerMenu(@ModelAttribute("burger") Burger burger, Model viewModel, @RequestParam("orderId") Long orderId) {
		//Model to Pass ID
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "burger.jsp";
	}
	
	//-----------------------------------
	//New Order : To Burger
	//Create New Order From Home
	//-----------------------------------
	@PostMapping("/order/burger")
	public String newOrder(@ModelAttribute("burger") Burger burger, Model viewModel) {
		//Starts new Order upon arriving
		Order newOrder = new Order();
		//Complete set to False
		newOrder.setComplete(false);
		//Save NewOrder to DB
		oService.saveOrder(newOrder);
		//Model to Pass ID
		viewModel.addAttribute("orderId", newOrder.getId());
		viewModel.addAttribute("order", newOrder);
		return "burger.jsp";
	}

	//Adding Burger to Order
	@PostMapping("/burger")
	public String orderBurger(@Valid @ModelAttribute("burger") Burger burger, BindingResult result, @RequestParam("orderId") Long orderId, Model viewModel) {		
		if(result.hasErrors()) {
			viewModel.addAttribute("orderId", orderId);
			Order modelOrder = this.oService.findOneOrder(orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());

			viewModel.addAttribute("order", oService.findOneOrder(orderId));
			return "burger.jsp";
		}
		//If No Burger is Chosen -- DO NOT SAVE to DB. Allows to order no Burger and go to FF
		if(burger.getType() == "") {
			viewModel.addAttribute("orderId", orderId);
			Order modelOrder = this.oService.findOneOrder(orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());
			viewModel.addAttribute("order", oService.findOneOrder(orderId));
			return "burger.jsp";
		}
		bService.saveBurger(burger);
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		//Add Burger to Order
		Order thisOrder = this.oService.findOneOrder(orderId);
		List<Burger> orderBurgs = thisOrder.getBurgers();
		orderBurgs.add(burger);
		//Save Order Change
		oService.saveOrder(thisOrder);
		return "burger.jsp";
	}

	
	//-----------------------------------
	//ORDERING FRIES
	//-----------------------------------
	@GetMapping("/fries")
	public String friesMenu(@ModelAttribute("fry") Fry fry, Model viewModel, @RequestParam("orderId") Long orderId) {
		//Get Order
		Order thisOrder = this.oService.findOneOrder(orderId);
		//Model to Pass ID
		viewModel.addAttribute("order", thisOrder);
		return "fries.jsp";
	}
	
	@PostMapping("/fries")
	public String orderfries(@Valid @ModelAttribute("fry") Fry fry, BindingResult result, @RequestParam("orderId") Long orderId) {
		if(result.hasErrors()) {
			return "fries.jsp";
		}
		this.fService.saveFry(fry);
		//Add Fry to Order
		Order thisOrder = this.oService.findOneOrder(orderId);
		List<Fry> orderFries = thisOrder.getFries();
		orderFries.add(fry);
		//Save Order Change
		oService.saveOrder(thisOrder);
		return "fries.jsp";
	}
	
	
	

	
	//LogOut
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
		
	
	
	
	
	
	
//	//Joining (Attending) an Event
//	@GetMapping("/join/{id}")
//	private String like(@PathVariable("id") Long id, HttpSession session){
//		Long userId = (Long)session.getAttribute("user_id");
//		Long eventId = id;
//		User attendee = this.uService.getOneUser(userId);
//		Event attendedEvent = this.eService.findOneEvent(eventId);
//		this.eService.addAttendee(attendee, attendedEvent);
//		return "redirect:/dashboard";
//	}
//
//	//Canceling (Unjoinin) an Event
//	@GetMapping("/unjoin/{id}")
//	private String unlike(@PathVariable("id") Long id, HttpSession session) {
//		Long userId = (Long)session.getAttribute("user_id");
//		Long eventId = id;
//		User attendee = this.uService.getOneUser(userId);
//		Event attendedEvent = this.eService.findOneEvent(eventId);
//		this.eService.removeAttendee(attendee, attendedEvent);
//		return "redirect:/dashboard";
//	}
	
	
//	//Creating new Event
//	@PostMapping("/events/new")
//	public String newEvent(@Valid @ModelAttribute("event") Event newEvent, BindingResult result, Model viewModel, HttpSession session) {
//		if(session.getAttribute("user_id") == null) {
//			return "redirect:/";
//		}
//		if(result.hasErrors()) {
//			Long userId = (Long)session.getAttribute("user_id");
//			viewModel.addAttribute("user_id", userId);
//			return "edit.jsp";
//		}
//		eService.saveEvent(newEvent);
//		return "redirect:/dashboard";
//	}

	
	
	
	
//	//Editing Event
//	@GetMapping("/events/{id}/edit")
//	public String viewEditEvent(@ModelAttribute("event") Event event, @PathVariable("id") Long id, HttpSession session, Model viewModel) {
//		Long userId = (Long)session.getAttribute("user_id");
//		viewModel.addAttribute("user_id", userId);
//		if(!eService.findHostId(id).equals(session.getAttribute("user_id"))) {
//			return "redirect:/dashboard";
//		}
//		if(session.getAttribute("user_id") == null) {
//			return "redirect:/";
//		}
//		viewModel.addAttribute("event", this.eService.findOneEvent(id));
//		return "edit.jsp";
//	}

//	//Editing Event Logic
//	@PostMapping("/events/{id}/edit")
//	public String editEvent(@Valid @ModelAttribute("event") Event updatedEvent, BindingResult result, Model viewModel, HttpSession session, @PathVariable("id") Long id) {
//		if(session.getAttribute("user_id") == null) {
//			return "redirect:/";
//		}
//		if(result.hasErrors()) {
//			Long userId = (Long)session.getAttribute("user_id");
//			viewModel.addAttribute("user_id", userId);
//			return "edit.jsp";
//		}
//		this.eService.saveEvent(updatedEvent);
//		return "redirect:/dashboard";
//	}

	
	
	//	//View Event
//	@GetMapping("/events/{id}")
//	public String viewEvent(@ModelAttribute("message") Message message, @PathVariable("id") Long id, Model viewModel, HttpSession session) {
//		if(session.getAttribute("user_id") == null) {
//			return "redirect:/";
//		}
//		viewModel.addAttribute("event", this.eService.findOneEvent(id));
//		viewModel.addAttribute("hostName", this.eService.findHostName(id));
//		return "showEvent.jsp";
//	}
	
//	//Add Message Logic
//	@PostMapping("/message/new")
//	public String newMessage(@Valid @ModelAttribute("message") Message message, BindingResult result, HttpSession session) {
//		if(result.hasErrors()) {
//			return "showEvent.jsp";
//		}
//		Message newMessage = this.mService.createMessage(message);
//		return "redirect:/events/" + newMessage.getEvent().getId();
//	}
	
//	//Delete
//	@GetMapping("/delete/{id}")
//	public String delete(@PathVariable("id") Long id) {
//		this.eService.deleteEvent(id);
//		return "redirect:/dashboard";
//	}
	
}
