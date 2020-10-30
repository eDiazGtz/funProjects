package com.edgar.burgernow.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edgar.burgernow.models.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository <Ingredient, Long> {
	List<Ingredient> findAll();

}
