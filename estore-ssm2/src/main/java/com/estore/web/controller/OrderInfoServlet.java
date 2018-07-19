package com.estore.web.controller;

import com.estore.entity.Line;
import com.estore.entity.Order;
import com.estore.service.ILineService;
import com.estore.service.IOrderService;
import com.estore.service.IOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet(name = "OrderInfoServlet", urlPatterns="/user/orderinfo")
@Controller
public class OrderInfoServlet extends HttpServlet {

    @Autowired
    private ILineService iLineService;

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/user/orderinfo")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String orderid = request.getParameter("orderid");
        if(orderid!=null){
            Order order = iOrderService.findById(Integer.parseInt(orderid));
            List<Line> lines = iLineService.findLines(Integer.parseInt(orderid));
            session.setAttribute("order", order);
            session.setAttribute("lines", lines);
            response.sendRedirect("orderinfo.jsp");
        }else{
            response.sendRedirect("order.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
