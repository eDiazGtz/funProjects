package com.edgar.burgernow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.burgernow.models.Burger;
import com.edgar.burgernow.repositories.BurgerRepository;

@Service
public class BurgerService {
	@Autowired
	private BurgerRepository bRepo;
	
	//Find All Burgers
	public List<Burger> findAllBurgers() {
		List<Burger> allBurgers = this.bRepo.findAll();
		return allBurgers;
	}
	
	//Find One Burger
	public Burger findOneBurger(Long id) {
		Burger burger = this.bRepo.findById(id).orElse(null);
		return burger;
	}
	
	
	//Save Burger
	public Burger saveBurger(Burger newBurger) {
		return this.bRepo.save(newBurger);
	}
	
	//Delete Burger
	public void deleteBurger(Long id) {
		this.bRepo.deleteById(id);
	}
	
//	//Add Ingredient
//	public void addIngredient(Ingredient ingredient, Burger burger) {
//		//Get the list from the Burger
//		List<Ingredient> ingredients = burger.getIngredients();
//		//Add new Ingredient
//		ingredients.add(ingredient);
//		//Update
//		this.bRepo.save(burger);
//	}
	
}
