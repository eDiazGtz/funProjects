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

	// Home Page (Root)
	@GetMapping("/")
	public String index(HttpSession session, Model viewModel) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "home.jsp";
		}
		viewModel.addAttribute("user_id", userId);
		User thisUser = this.uService.findOneUser(userId);
		viewModel.addAttribute("user", thisUser);
		List<Order> guestOrders = thisUser.getOrders();
		viewModel.addAttribute("guestOrders", guestOrders);
		return "home.jsp";
	}

	// Login
	@GetMapping("/login")
	public String loginPage(@ModelAttribute("user") User user) {
		return "login.jsp";
	}

	// Login to Session
	@PostMapping("/login")
	public String login(HttpSession session, @RequestParam("lemail") String email,
			@RequestParam("lpassword") String password, RedirectAttributes redirectAttrs, Model viewModel) {
		if (!this.uService.authenticateUser(email, password)) {
			redirectAttrs.addFlashAttribute("loginError", "Invalid Credentials");
			return "redirect:/login";
		}
		User user = this.uService.getUserByEmail(email);
		session.setAttribute("user_id", user.getId());
		return "redirect:/";
	}

	// Register Page
	@GetMapping("/register")
	public String registerPage(@ModelAttribute("user") User user) {
		return "register.jsp";
	}

	// Register User
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		validator.validate(user, result);
		if (result.hasErrors()) {
			// If validation errors send them back to Register Page
			return "register.jsp";
		}
		User newUser = this.uService.registerUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/";
	}

	// -----------------------------------
	// EDIT ACCOUNT
	// -----------------------------------
	@GetMapping("/account")
	public String viewAccount(@ModelAttribute("user") User user, Model viewModel, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		viewModel.addAttribute("user", uService.findOneUser(userId));
		return "account.jsp";
	}

	// Edit FirstName
	@GetMapping("/account/first/{id}")
	public String getAccount(@ModelAttribute("user") User user, Model viewModel, HttpSession session,
			@PathVariable("id") Long id) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		User thisUser = this.uService.findOneUser(id);
		if (thisUser == null) {
			return "redirect:/";
		}
		User sessUser = uService.findOneUser(userId);
		viewModel.addAttribute("user", uService.findOneUser(userId));
		if (thisUser.getId() == sessUser.getId()) {
			return "account.jsp";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/account/first/{id}")
	public String editFirstName(@ModelAttribute("user") User user, @PathVariable("id") Long id, Model viewModel, HttpSession session, @RequestParam("firstName") String firstName) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		User thisUser = this.uService.findOneUser(id);
		if (thisUser == null) {
			return "redirect:/";
		}
		User sessUser = uService.findOneUser(userId);
		viewModel.addAttribute("user", uService.findOneUser(userId));
		if (thisUser.getId() == sessUser.getId()) {
			thisUser.setFirstName(firstName);
			this.uService.saveUser(thisUser);
			viewModel.addAttribute("user", uService.findOneUser(userId));
			return "account.jsp";
		} else {
			return "redirect:/";
		}
	}

	// Edit LastName
	@GetMapping("/account/last/{id}")
	public String getForLastName(@ModelAttribute("user") User user, Model viewModel, HttpSession session,
			@PathVariable("id") Long id) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		User thisUser = this.uService.findOneUser(id);
		if (thisUser == null) {
			return "redirect:/";
		}
		User sessUser = uService.findOneUser(userId);
		viewModel.addAttribute("user", uService.findOneUser(userId));
		if (thisUser.getId() == sessUser.getId()) {
			return "account.jsp";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/account/last/{id}")
	public String editLastName(@Valid @ModelAttribute("user") User user, BindingResult result,
			@PathVariable("id") Long id, Model viewModel, HttpSession session,
			@RequestParam("lastName") String lastName) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		if (result.hasErrors()) {
			viewModel.addAttribute("user", uService.findOneUser(userId));
			return "redirect:/account/last/" + userId;
		}
		User thisUser = this.uService.findOneUser(id);
		if (thisUser == null) {
			return "redirect:/";
		}
		User sessUser = uService.findOneUser(userId);
		viewModel.addAttribute("user", uService.findOneUser(userId));
		if (thisUser.getId() == sessUser.getId()) {
			thisUser.setLastName(lastName);
			this.uService.saveUser(thisUser);
			viewModel.addAttribute("user", uService.findOneUser(userId));
			return "account.jsp";
		} else {
			return "redirect:/";
		}
	}

	// Edit Email
	@GetMapping("/account/email/{id}")
	public String getForEmail(@ModelAttribute("user") User user, Model viewModel, HttpSession session,
			@PathVariable("id") Long id) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		User thisUser = this.uService.findOneUser(id);
		if (thisUser == null) {
			return "redirect:/";
		}
		User sessUser = uService.findOneUser(userId);
		viewModel.addAttribute("user", uService.findOneUser(userId));
		if (thisUser.getId() == sessUser.getId()) {
			return "account.jsp";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/account/email/{id}")
	public String editEmail(@Valid @ModelAttribute("user") User user, BindingResult result, @PathVariable("id") Long id,
			Model viewModel, HttpSession session, @RequestParam("email") String email) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		if (result.hasErrors()) {
			viewModel.addAttribute("user", uService.findOneUser(userId));
			return "redirect:/account/email/" + userId;
		}
		User thisUser = this.uService.findOneUser(id);
		if (thisUser == null) {
			return "redirect:/";
		}
		User sessUser = uService.findOneUser(userId);
		viewModel.addAttribute("user", uService.findOneUser(userId));
		if (thisUser.getId() == sessUser.getId()) {
			thisUser.setEmail(email);
			this.uService.saveUser(thisUser);
			viewModel.addAttribute("user", uService.findOneUser(userId));
			return "account.jsp";
		} else {
			return "redirect:/";
		}
	}

	// -----------------------------------
	// New Order : To Burger
	// Create New Order From Home
	// -----------------------------------
	@PostMapping("/new/order/burger")
	public String newOrder(@ModelAttribute("burger") Burger burger, Model viewModel, HttpSession session) {
		// Starts new Order upon arriving
		Order newOrder = new Order();
		// Complete set to False
		newOrder.setComplete(false);
		// Check if User is Logged In -- Assign as Guest
		Long userId = (Long) session.getAttribute("user_id");
		if (userId != null) {
			User newGuest = uService.findOneUser(userId);
			newOrder.setGuest(newGuest);
		}
		// Save NewOrder to DB
		oService.saveOrder(newOrder);
		// Model to Pass ID
		viewModel.addAttribute("orderId", newOrder.getId());
		viewModel.addAttribute("order", newOrder);
		Long orderId = newOrder.getId();
		return "redirect:/burger?orderId=" + orderId;
	}

	// -----------------------------------
	// ORDERING BURGERS
	// -----------------------------------
	@GetMapping("/burger")
	public String viewBurger(@ModelAttribute("burger") Burger burger, Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		// Security
		// If Order has Guest (if Guest is not null)
		if (modelOrder.getGuest() != null) {
			// Check if Session is null
			Long userId = (Long) session.getAttribute("user_id");
			if (userId == null) {
				return "redirect:/";
			}
			// Check if User == Guest
			User sessUser = uService.findOneUser(userId);
			User guestUser = modelOrder.getGuest();
			if (guestUser.getId() != sessUser.getId()) {
				return "redirect:/";
			}
			return "burger.jsp";
		}
		return "burger.jsp";
	}

	// Adding Burger to Order
	@PostMapping("/burger")
	public String orderBurger(@Valid @ModelAttribute("burger") Burger burger, BindingResult result, @RequestParam("orderId") Long orderId, Model viewModel) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());

		if (result.hasErrors()) {
			return "redirect:/burger?orderId=" + orderId;
		}
		// If No Burger is Chosen -- DO NOT SAVE to DB. Allows to order no Burger and go
		// to FF
		if (burger.getType() == "") {
			return "redirect:/burger?orderId=" + orderId;
		}
		bService.saveBurger(burger); // Works
		// Add Burger to Order
		List<Burger> orderBurgs = modelOrder.getBurgers();
		orderBurgs.add(burger);
		// Save Order Change
		oService.saveOrder(modelOrder);
		return "redirect:/burger?orderId=" + orderId;
	}

	// -----------------------------------
	// ORDERING FRIES
	// -----------------------------------
	// Adding Fry to Order
	@GetMapping("/fries")
	public String viewFryMenu(@ModelAttribute("fries") Fry fry, Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		// Security
		// If Order has Guest (if Guest is not null)
		if (modelOrder.getGuest() != null) {
			// Check if Session is null
			Long userId = (Long) session.getAttribute("user_id");
			if (userId == null) {
				return "redirect:/";
			}
			// Check if User == Guest
			User sessUser = uService.findOneUser(userId);
			User guestUser = modelOrder.getGuest();
			if (guestUser.getId() != sessUser.getId()) {
				return "redirect:/";
			}
			return "fries.jsp";
		}
		return "fries.jsp";
	}

	@PostMapping("/fries")
	public String orderFry(@Valid @ModelAttribute("fries") Fry fry, BindingResult result, @RequestParam("orderId") Long orderId, Model viewModel) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		if (result.hasErrors()) {
			return "redirect:/fries?orderId=" + orderId;
		}
		// If No Fry is Chosen -- DO NOT SAVE to DB. Allows to order no FF
		if (fry.getType() == "") {
			return "redirect:/fries?orderId=" + orderId;
		}
		fService.saveFry(fry);
		// Add Fry to Order
		List<Fry> orderFries = modelOrder.getFries();
		orderFries.add(fry);
		// Save Order Change
		oService.saveOrder(modelOrder);
		return "redirect:/fries?orderId=" + orderId;
	}

	// -----------------------------------
	// CHECKOUT
	// -----------------------------------
	@GetMapping("/checkout")
	public String postCheckout(Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		viewModel.addAttribute("order", modelOrder);
		// Security
		// If Order has Guest (if Guest is not null)
		if (modelOrder.getGuest() != null) {
			// Check if Session is null
			Long userId = (Long) session.getAttribute("user_id");
			if (userId == null) {
				return "redirect:/";
			}
			// Check if User == Guest
			User sessUser = uService.findOneUser(userId);
			User guestUser = modelOrder.getGuest();
			if (guestUser.getId() != sessUser.getId()) {
				return "redirect:/";
			}
			return "checkout.jsp";
		}
		return "checkout.jsp";
	}

	// -----------------------------------
	// PURCHASE
	// -----------------------------------
	@PostMapping("/purchase")
	public String purchase(Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		Order finalOrder = oService.findOneOrder(orderId);
		if(finalOrder.getBurgers().size() == 0 && finalOrder.getFries().size() == 0) {
			return "redirect:/checkout?orderId=" + orderId;
		}
			finalOrder.setComplete(true);
			Long userId = (Long) session.getAttribute("user_id");
			if (userId == null) {
				oService.saveOrder(finalOrder);
				return "redirect:/";
			} else {
				User user = this.uService.findOneUser(userId);
				finalOrder.setGuest(user);
				oService.saveOrder(finalOrder);
				return "redirect:/";
		}
	}

	// -----------------------------------
	// EDIT SINGLE ITEM
	// -----------------------------------
	@GetMapping("/edit/burger/{id}")
	public String editBurger(@PathVariable("id") Long id, @ModelAttribute("burger") Burger burger, Model viewModel, @RequestParam("orderId") Long orderId, HttpSession session) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		//Security
		Long userId = (Long) session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if (modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if (sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		return "editBurger.jsp";
	}

	@PostMapping("/finalize/burger/{id}")
	public String finalizeBurger(@Valid @ModelAttribute("burger") Burger updatedBurger, BindingResult result, HttpSession session, Model viewModel, @PathVariable("id") Long id, @RequestParam("orderId") Long orderId) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		//Security
		Long userId = (Long) session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if (modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if (sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		if (result.hasErrors()) {
			return "editBurger.jsp";
		}
		this.bService.saveBurger(updatedBurger);
		return "redirect:/checkout?orderId=" + orderId;
	}

	// FF Edit
	@GetMapping("/edit/fry/{id}")
	public String editFries(@PathVariable("id") Long id, @ModelAttribute("fries") Fry fry, Model viewModel, HttpSession session, @RequestParam("orderId") Long orderId) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		viewModel.addAttribute("burgQty", modelOrder.getBurgers().size());
		viewModel.addAttribute("fryQty", modelOrder.getFries().size());
		//Security
		Long userId = (Long) session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if (modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if (sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		return "editFries.jsp";
	}

	@PostMapping("/finalize/fry/{id}")
	public String finalizeFry(@Valid @ModelAttribute("fries") Fry updatedFry, BindingResult result, Model viewModel, HttpSession session, @PathVariable("id") Long id, @RequestParam("orderId") Long orderId) {
		viewModel.addAttribute("orderId", orderId);
		Order modelOrder = this.oService.findOneOrder(orderId);
		//Security
		Long userId = (Long) session.getAttribute("user_id");
		User sessUser = uService.findOneUser(userId);
		if (modelOrder.getGuest() != null) {
			User modelUser = modelOrder.getGuest();
			if (sessUser.getId() != modelUser.getId()) {
				return "redirect:/";
			}
		}
		if (result.hasErrors()) {
			return "editFries.jsp";
		}
		this.fService.saveFry(updatedFry);
		return "redirect:/checkout?orderId=" + orderId;
	}

	
	// -----------------------------------
	// DELETE @ CHECKOUT
	// -----------------------------------
	// Checkout Delete Burger
	@PostMapping("/delete/burger/{id}")
	public String deleteBurger(@PathVariable("id") Long id, Model viewModel, @RequestParam("orderId") Long orderId) {
		bService.deleteBurger(id);
		viewModel.addAttribute("orderId", orderId);
		return "redirect:/checkout?orderId=" + orderId;
		}

	// Checkout Delete Fry
	@PostMapping("/delete/fry/{id}")
	public String deleteFry(@PathVariable("id") Long id, Model viewModel, @RequestParam("orderId") Long orderId) {
		fService.deleteFry(id);
		viewModel.addAttribute("orderId", orderId);
		return "redirect:/checkout?orderId=" + orderId;	
		}

	// LogOut
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
