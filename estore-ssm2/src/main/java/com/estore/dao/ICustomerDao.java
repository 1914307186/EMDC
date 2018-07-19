package com.estore.dao;


import com.estore.entity.Customer;

public interface ICustomerDao  {

	long selectCustomerKey();
	Customer findByName(String name);
	void saveCustomer(Customer customer);
	void updateCustomer(Customer customer);
}
