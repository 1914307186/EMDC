package com.estore.service;

import com.estore.common.exception.CustomerException;
import com.estore.dao.ICustomerDao;
import com.estore.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ICustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao iCustomerDao;

    @Override
    public void register(Customer customer) throws CustomerException {
        long id = iCustomerDao.selectCustomerKey();
        customer.setId(id);
        Customer customerByName = iCustomerDao.findByName(customer.getName());
        if(customerByName!=null){
            throw new CustomerException("用户已存在。");
        }
        iCustomerDao.saveCustomer(customer);
    }

    @Override
    public Customer login(String name, String password) throws CustomerException {
        Customer customer = iCustomerDao.findByName(name);
        if(customer!=null && customer.getPassword()!=null){
            if(customer.getPassword().equals(password)){
                return customer;
            }
            throw new CustomerException("登录失败，密码错误！");
        }
        throw new CustomerException("登录失败，找不到用户！");
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerException {
        if (customer.getName() != null) {
            iCustomerDao.updateCustomer(customer);
            return;
        }
        throw new CustomerException("修改信息失败，用户名为空！");
    }
}
