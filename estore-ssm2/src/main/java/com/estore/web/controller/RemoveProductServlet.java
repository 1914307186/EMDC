package com.estore.web.controller;


import com.estore.entity.ShoppingCar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RemoveProductServlet", urlPatterns = "/removeProduct")
public class RemoveProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productid = request.getParameter("productid");
        HttpSession session = request.getSession();
        ShoppingCar shoppingCar;
        if(productid!=null){
            int id = Integer.parseInt(productid);
            Object obj = session.getAttribute("shopping_car");
            if(obj!=null){
                shoppingCar = (ShoppingCar) obj;
                shoppingCar.getLines().remove((long) id);
                session.setAttribute("shopping_car", shoppingCar);
            }
        }
        response.sendRedirect("shopcart.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
