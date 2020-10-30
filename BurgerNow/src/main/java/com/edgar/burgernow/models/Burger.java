package com.edgar.burgernow.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="burgers")
public class Burger {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int qty;
	private String type;
	private Boolean sauce;
	private Boolean lettuce;
	private Boolean tomato;
	private Boolean onion;
	private Boolean ketchup;
	private Boolean pickles;
	
	//Relationships
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order bOrder;
	
//	@ManyToMany(fetch=FetchType.LAZY)
//	@JoinTable(
//			name="burgers_ingredients",
//			joinColumns = @JoinColumn(name="burger_id"),
//			inverseJoinColumns = @JoinColumn(name="ingredient_id")
//	)
//	private List<Ingredient> ingredients;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-DD HH:mm:ss")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-DD HH:mm:ss")
	private Date updatedAt;

	@PrePersist
	protected void OnCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void OnUpdate() {
		this.updatedAt = new Date();
	}

	
	public Burger() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Order getbOrder() {
		return bOrder;
	}

	public void setbOrder(Order bOrder) {
		this.bOrder = bOrder;
	}

//	public List<Ingredient> getIngredients() {
//		return ingredients;
//	}
//
//	public void setIngredients(List<Ingredient> ingredients) {
//		this.ingredients = ingredients;
//	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getSauce() {
		return sauce;
	}

	public void setSauce(Boolean sauce) {
		this.sauce = sauce;
	}

	public Boolean getLettuce() {
		return lettuce;
	}

	public void setLettuce(Boolean lettuce) {
		this.lettuce = lettuce;
	}

	public Boolean getTomato() {
		return tomato;
	}

	public void setTomato(Boolean tomato) {
		this.tomato = tomato;
	}

	public Boolean getOnion() {
		return onion;
	}

	public void setOnion(Boolean onion) {
		this.onion = onion;
	}

	public Boolean getKetchup() {
		return ketchup;
	}

	public void setKetchup(Boolean ketchup) {
		this.ketchup = ketchup;
	}

	public Boolean getPickles() {
		return pickles;
	}

	public void setPickles(Boolean pickles) {
		this.pickles = pickles;
	}
	
	

	
}
