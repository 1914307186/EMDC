package com.estore.web.controller;

import com.estore.common.exception.OrderException;
import com.estore.service.IOrderService;
import com.estore.service.IOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Contended;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "RemoveServlet", urlPatterns = "/user/removeOrder")
@Controller
public class RemoveServlet extends HttpServlet {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/user/removeOrder")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderid = request.getParameter("orderid");
        try {
            iOrderService.deleteOrder(Long.parseLong(orderid));
            response.sendRedirect("order");
        } catch (OrderException e) {
            System.out.println(e.toString());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
