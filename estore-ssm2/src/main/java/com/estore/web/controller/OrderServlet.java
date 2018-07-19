package com.estore.web.controller;

import com.estore.entity.*;
import com.estore.common.exception.OrderException;
import com.estore.service.IOrderService;
import com.estore.service.IOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet(name = "OrderServlet", urlPatterns = "/user/order")
@Controller
public class OrderServlet extends HttpServlet {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/user/order")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("customer");
        if(obj!=null){
            Customer customer = (Customer) obj;
            try {
                List<Order> orders = iOrderService.findByCustomerId(customer.getId());
                session.setAttribute("orders", orders);
                response.sendRedirect("order.jsp");
            } catch (OrderException e) {
                System.out.println(e.toString());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
