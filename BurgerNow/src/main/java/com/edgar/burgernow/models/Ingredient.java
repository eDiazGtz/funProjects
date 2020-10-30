package com.edgar.burgernow.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="ingredients")
public class Ingredient {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	
	//Relationships
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="burgers_ingredients",
			joinColumns = @JoinColumn(name="ingredient_id"),
			inverseJoinColumns = @JoinColumn(name="burger_id")
	)
	private List<Burger> iBurger;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="fries_ingredients",
			joinColumns = @JoinColumn(name="ingredient_id"),
			inverseJoinColumns = @JoinColumn(name="fry_id")
	)
	private List<Fry> iFry;

	
	public Ingredient() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Burger> getiBurger() {
		return iBurger;
	}


	public void setiBurger(List<Burger> iBurger) {
		this.iBurger = iBurger;
	}


	public List<Fry> getiFry() {
		return iFry;
	}


	public void setiFry(List<Fry> iFry) {
		this.iFry = iFry;
	}



	
	

}
