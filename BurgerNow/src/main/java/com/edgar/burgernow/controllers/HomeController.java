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
		
	@PostMapping("/order/checkout")
	public String getCheckout(Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		Order modelOrder = this.oService.findOneOrder(orderId);
		Long userId = (Long)session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if(modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if(sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		//Model to Pass ID
		viewModel.addAttribute("orderId", orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		viewModel.addAttribute("order", modelOrder);
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
	public String editBurger(@PathVariable("id") Long id, @ModelAttribute("burger") Burger burger, Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		Order modelOrder = this.oService.findOneOrder(orderId);
		Long userId = (Long)session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if(modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if(sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		viewModel.addAttribute("burger", bService.findOneBurger(id));
		viewModel.addAttribute("orderId", orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		viewModel.addAttribute("order", modelOrder);
		return "editBurger.jsp";
	}
	
	@PostMapping("/finalize/burger/{id}")
	public String finalizeBurger(@Valid @ModelAttribute("burger") Burger updatedBurger, BindingResult result, HttpSession session, Model viewModel, @PathVariable("id") Long id, @RequestParam("orderId") Long orderId) {
		Order modelOrder = this.oService.findOneOrder(orderId);
		Long userId = (Long)session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if(modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if(sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		if(result.hasErrors()) {
			viewModel.addAttribute("burger", bService.findOneBurger(id));
			viewModel.addAttribute("orderId", orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());
			viewModel.addAttribute("order", modelOrder);
			return "editBurger.jsp";
		}
		viewModel.addAttribute("orderId", orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		viewModel.addAttribute("order", oService.findOneOrder(orderId));
		this.bService.saveBurger(updatedBurger);
		return "checkout.jsp";
	}
	
	
	//FF Edit	
	@PostMapping("/edit/fry/{id}")
	public String editFries(@PathVariable("id") Long id, @ModelAttribute("fries") Fry fry, Model viewModel, HttpSession session, @RequestParam("orderId") Long orderId) {
		Order modelOrder = this.oService.findOneOrder(orderId);
		Long userId = (Long)session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if(modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if(sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		viewModel.addAttribute("fries", fService.findOneFry(id));
		viewModel.addAttribute("orderId", orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		viewModel.addAttribute("order", modelOrder);
		return "editFries.jsp";
	}
	
	@PostMapping("/finalize/fry/{id}")
	public String finalizeFry(@Valid @ModelAttribute("fries") Fry updatedFry, BindingResult result, Model viewModel, HttpSession session, @PathVariable("id") Long id, @RequestParam("orderId") Long orderId) {
		Order modelOrder = this.oService.findOneOrder(orderId);
		Long userId = (Long)session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if(modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if(sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		if(result.hasErrors()) {
			viewModel.addAttribute("fries", fService.findOneFry(id));
			viewModel.addAttribute("orderId", orderId);
			viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
			viewModel.addAttribute("fryQty", modelOrder.getFries().size());
			viewModel.addAttribute("order", oService.findOneOrder(orderId));
			return "editFries.jsp";
		}
		viewModel.addAttribute("orderId", orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		viewModel.addAttribute("order", modelOrder);
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
	
}
