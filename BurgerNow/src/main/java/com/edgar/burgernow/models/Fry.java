package com.edgar.burgernow.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="fries")
public class Fry {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int qty;
	private String type;
	
	
	//Relationships
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order fOrder;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="fries_ingredients",
			joinColumns = @JoinColumn(name="fry_id"),
			inverseJoinColumns = @JoinColumn(name="ingredient_id")
	)
	private List<Ingredient> ingredients;	
	
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

	public Fry() {
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

	public Order getfOrder() {
		return fOrder;
	}

	public void setfOrder(Order fOrder) {
		this.fOrder = fOrder;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

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

	
	
	

}
