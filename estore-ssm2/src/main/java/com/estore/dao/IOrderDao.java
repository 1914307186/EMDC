package com.estore.dao;


import com.estore.entity.Order;

import java.util.List;

public interface IOrderDao  {

	long saveOrder(Order order);
	Order findById(Long id);
	List<Order> findOrderByCustomerId(Long id);
	void deleteOrder(long id);
}
