package com.estore.service;


import com.estore.common.exception.CustomerException;
import com.estore.entity.Customer;

public interface ICustomerService {
	void register(Customer customer) throws CustomerException;
	Customer login(String name, String password) throws CustomerException;
	void updateCustomer(Customer customer) throws CustomerException;
}
