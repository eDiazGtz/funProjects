package com.edgar.burgernow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.burgernow.models.Ingredient;
import com.edgar.burgernow.repositories.IngredientRepository;

@Service
public class IngredientService {
	@Autowired
	private IngredientRepository iRepo;
	
	//Find All Ingredients
	public List<Ingredient> findAllIngredients() {
		List<Ingredient> allIngredients = this.iRepo.findAll();
		return allIngredients;
	}
	
	//Find One Ingredient
	public Ingredient findOneIngredient(Long id) {
		Ingredient ingredient = this.iRepo.findById(id).orElse(null);
		return ingredient;
	}

}
