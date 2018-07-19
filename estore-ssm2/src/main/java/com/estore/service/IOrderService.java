package com.estore.service;

import com.estore.common.exception.OrderException;
import com.estore.entity.Customer;
import com.estore.entity.Line;
import com.estore.entity.Order;

import java.util.Collection;
import java.util.List;

public interface IOrderService {
	void confirmOrder(Customer customer, Order order, Collection<Line> lines) throws OrderException;
	void deleteOrder(Long id) throws OrderException;
	Order findById(long id);
	List<Order> findByCustomerId(Long id) throws OrderException;
}
