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
import org.springframework.web.bind.annotation.PathVariable;
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
		viewModel.addAttribute("user", this.uService.findOneUser(userId));
		User thisUser = this.uService.findOneUser(userId);
		List<Order> guestOrders = thisUser.getOrders();
		viewModel.addAttribute("guestOrders", guestOrders);
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
	//Account
	//-----------------------------------
	@GetMapping("/account")
	public String viewAccount(@ModelAttribute("user") User user, Model viewModel, HttpSession session) {
		Long userId = (Long)session.getAttribute("user_id");
		if(userId == null) {
			return "redirect:/";
		}
		viewModel.addAttribute("user", uService.findOneUser(userId));
		return "account.jsp";
	}
	
	
	@PostMapping("/account/first/{id}")
	public String editBurger(@PathVariable("id") Long id, Model viewModel, HttpSession session, @RequestParam("firstName") String firstName) {
		User thisUser = this.uService.findOneUser(id);
		Long userId = (Long)session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		viewModel.addAttribute("pass", thisUser.getPassword());
		if(thisUser.getId() == sessUser.getId()) {

			thisUser.setFirstName(firstName);
			this.uService.saveUser(thisUser);
			return "account.jsp";
	} else {
		System.out.println("takoyaki2");
		System.out.println(thisUser.getId());
		System.out.println(session.getAttribute("user_id"));
		return "redirect:/";
	}

	}
	

	//-----------------------------------
	//ORDERING BURGERS
	//-----------------------------------
	@PostMapping("/order/burger")
	public String burgerMenu(@ModelAttribute("burger") Burger burger, Model viewModel, @RequestParam("orderId") Long orderId) {
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
	@PostMapping("/new/order/burger")
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
	//Adding Fry to Order
	@PostMapping("/order/fries")
	public String fryMenu(@ModelAttribute("fries") Fry fry, Model viewModel, @RequestParam("orderId") Long orderId) {
		//Model to Pass ID
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "fries.jsp";
	}


	@PostMapping("/fries")
	public String orderFry(@Valid @ModelAttribute("fries") Fry fry, BindingResult result, @RequestParam("orderId") Long orderId, Model viewModel) {		
		if(result.hasErrors()) {
			viewModel.addAttribute("orderId", orderId);
			Order modelOrder = this.oService.findOneOrder(orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());

			viewModel.addAttribute("order", oService.findOneOrder(orderId));
			return "fries.jsp";
		}
		//If No Fry is Chosen -- DO NOT SAVE to DB. Allows to order no FF
		if(fry.getType() == "") {
			viewModel.addAttribute("orderId", orderId);
			Order modelOrder = this.oService.findOneOrder(orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());
			viewModel.addAttribute("order", oService.findOneOrder(orderId));
			return "fries.jsp";
		}
		fService.saveFry(fry);
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		//Add Fry to Order
		Order thisOrder = this.oService.findOneOrder(orderId);
		List<Fry> orderFries = thisOrder.getFries();
		orderFries.add(fry);
		//Save Order Change
		oService.saveOrder(thisOrder);
		return "fries.jsp";
	}

	//-----------------------------------
	//CHECKOUT
	//-----------------------------------
	
	//Add functionality -- if (order.guest is not null && guestID == sessionID) --> allow view of checkout item. 
	//To secure people brute forcing checkout items that belong to users --- check that order has guest.
	//If it does, check that the guestID == SessionID so that other guests cannot brute force either. 
	
	@PostMapping("/order/checkout")
	public String getCheckout(Model viewModel, @RequestParam("orderId") Long orderId) {
		//Model to Pass ID
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "checkout.jsp";
	}

	@PostMapping("/checkout")
	public String postCheckout(Model viewModel, @RequestParam("orderId") Long orderId) {
		//Model to Pass ID
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "checkout.jsp";
	}

	
	@PostMapping("/purchase")
	public String purchase(Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		Order finalOrder = oService.findOneOrder(orderId);
		finalOrder.setComplete(true);
		Long userId = (Long)session.getAttribute("user_id");
		if(userId == null) {
			oService.saveOrder(finalOrder);
			return "redirect:/";
		} else {
		User user = this.uService.findOneUser(userId);
		finalOrder.setGuest(user);
		oService.saveOrder(finalOrder);
		return "redirect:/";
		}
	}

	

	//-----------------------------------
	//EDIT SINGLE ITEM
	//-----------------------------------
	@PostMapping("/edit/burger/{id}")
	public String editBurger(@PathVariable("id") Long id, @ModelAttribute("burger") Burger burger, Model viewModel, @RequestParam("orderId") Long orderId) {
		viewModel.addAttribute("burger", bService.findOneBurger(id));
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "editBurger.jsp";
	}
	
	@PostMapping("/finalize/burger/{id}")
	public String finalizeBurger(@Valid @ModelAttribute("burger") Burger updatedBurger, BindingResult result, Model viewModel, @PathVariable("id") Long id, @RequestParam("orderId") Long orderId) {
		if(result.hasErrors()) {
			viewModel.addAttribute("burger", bService.findOneBurger(id));
			viewModel.addAttribute("orderId", orderId);
			Order modelOrder = this.oService.findOneOrder(orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());

			viewModel.addAttribute("order", oService.findOneOrder(orderId));
			return "editBurger.jsp";
		}
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		this.bService.saveBurger(updatedBurger);
		return "checkout.jsp";
	}
	
	
	//FF Edit	
	@PostMapping("/edit/fry/{id}")
	public String editFries(@PathVariable("id") Long id, @ModelAttribute("fries") Fry fry, Model viewModel, @RequestParam("orderId") Long orderId) {
		viewModel.addAttribute("fries", fService.findOneFry(id));
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "editFries.jsp";
	}
	
	@PostMapping("/finalize/fry/{id}")
	public String finalizeFry(@Valid @ModelAttribute("fries") Fry updatedFry, BindingResult result, Model viewModel, @PathVariable("id") Long id, @RequestParam("orderId") Long orderId) {
		if(result.hasErrors()) {
			viewModel.addAttribute("fries", fService.findOneFry(id));
			viewModel.addAttribute("orderId", orderId);
			Order modelOrder = this.oService.findOneOrder(orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());

			viewModel.addAttribute("order", oService.findOneOrder(orderId));
			return "editFries.jsp";
		}
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		this.fService.saveFry(updatedFry);
		return "checkout.jsp";
	}


	
	
	
	//-----------------------------------
	//DELETE @ CHECKOUT
	//-----------------------------------
	//Checkout Delete Burger
	@PostMapping("/delete/burger/{id}")
	public String deleteBurger(@PathVariable("id") Long id, Model viewModel, @RequestParam("orderId") Long orderId) {
		bService.deleteBurger(id);
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "checkout.jsp";
	}
	
	//Checkout Delete Fry
	@PostMapping("/delete/fry/{id}")
	public String deleteFry(@PathVariable("id") Long id, Model viewModel, @RequestParam("orderId") Long orderId) {
		fService.deleteFry(id);
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		return "checkout.jsp";
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
