package com.estore.service;

import com.estore.common.exception.OrderException;
import com.estore.dao.ILineDao;
import com.estore.dao.IOrderDao;
import com.estore.entity.Customer;
import com.estore.entity.Line;
import com.estore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao iOrderDao;

    @Autowired
    private ILineDao iLineDao;


    @Override
    public void confirmOrder(Customer customer, Order order, Collection<Line> lines) throws OrderException {
//        long id = sqlSession.selectOne("selectOrderKey");
//        order.setId(id);
        order.setCustomer(customer);
        // 保存 order
        iOrderDao.saveOrder(order);
        // 保存 line
        for (Line line : lines) {
            line.setOrder(order);
            iLineDao.addLine(line);
        }

    }

    @Override
    public void deleteOrder(Long id) throws OrderException {
        iOrderDao.deleteOrder(id);
    }

    @Override
    public Order findById(long id) {
        Order order = iOrderDao.findById(id);
        return order;
    }

    @Override
    public List<Order> findByCustomerId(Long id) throws OrderException {
        List<Order> orders = iOrderDao.findOrderByCustomerId(id);
        return orders;
    }
}
