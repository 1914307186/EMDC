package com.estore.web.controller;

import com.estore.common.exception.OrderException;
import com.estore.entity.Customer;
import com.estore.entity.Line;
import com.estore.entity.Order;
import com.estore.entity.ShoppingCar;
import com.estore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

//@WebServlet(name = "ConfirmOrderServlet", urlPatterns = "/confirmOrder")
@Controller
public class ConfirmOrderServlet extends HttpServlet {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/confirmOrder")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Order order = new Order();
        Customer customer;
        Collection<Line> lines;
        Object obj = session.getAttribute("shopping_car");
        Object customerObj = session.getAttribute("customer");
        if(obj!=null && customerObj!=null){
            ShoppingCar shoppingCar = (ShoppingCar)obj;
            order.setCost(shoppingCar.getCost());
            Map<Long, Line> linesMap = shoppingCar.getLines();
            lines = linesMap.values();
            customer = (Customer) customerObj;
            order.setOrderDate(new Date());
            try {
                iOrderService.confirmOrder(customer, order, lines);
                session.setAttribute("order", order);
                session.setAttribute("lines", lines);
                session.removeAttribute("shopping_car");
                response.sendRedirect("user/orderinfo.jsp");
            } catch (OrderException e) {
                System.out.println(e.toString());
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
