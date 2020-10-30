package com.edgar.burgernow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.burgernow.models.Order;
import com.edgar.burgernow.repositories.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository oRepo;
	
	//Find All Orders
	public List<Order> findAllOrders() {
		List<Order> allOrders = this.oRepo.findAll();
		return allOrders;
	}
	
	//Find One Order
	public Order findOneOrder(Long id) {
		Order order = this.oRepo.findById(id).orElse(null);
		return order;
	}
	
	//Create/Update Order
	public Order saveOrder(Order newOrder) {
		Order order = this.oRepo.save(newOrder);
		return order;
	}
	
	//Find By Complete == True
	
	
	//Don't need to Delete for now
	
	
}
