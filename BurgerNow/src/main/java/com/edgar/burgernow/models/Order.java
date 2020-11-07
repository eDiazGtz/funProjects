package com.edgar.burgernow.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Boolean complete;
	private BigDecimal total;
	
	//Relationships
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User guest;
	
	@OneToMany(mappedBy="bOrder",fetch=FetchType.LAZY)
	private List<Burger> burgers;

	@OneToMany(mappedBy="fOrder",fetch=FetchType.LAZY)
	private List<Fry> fries;

	
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

	
	
	public Order() {
	}

	
	//Methods
	//Set Total Price
	public void setFinalTotal() {
		List<Burger> allBurgs = this.getBurgers();
		BigDecimal subtotal = new BigDecimal("0.00");
		for(int b=0; b<allBurgs.size(); b++) {
			Burger loopBurg = allBurgs.get(b);
			subtotal = subtotal.add(loopBurg.getPrice());
		}
		List<Fry> allFries = this.getFries();
		for(int f=0; f<allFries.size(); f++) {
			Fry loopFries = allFries.get(f);
			subtotal = subtotal.add(loopFries.getPrice());
		}
		BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.08));
		BigDecimal ttl = subtotal.add(tax);
		this.total = ttl;
	}

	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getGuest() {
		return guest;
	}

	public void setGuest(User guest) {
		this.guest = guest;
	}

	public List<Burger> getBurgers() {
		return burgers;
	}

	public void setBurgers(List<Burger> burgers) {
		this.burgers = burgers;
	}

	public List<Fry> getFries() {
		return fries;
	}

	public void setFries(List<Fry> fries) {
		this.fries = fries;
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

	public Boolean getComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	
	
	
	

}
