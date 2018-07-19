package com.estore.web.controller;

import com.estore.entity.ShoppingCar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateProductServlet", urlPatterns = "/updateProduct")
public class UpdateProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("shopping_car");
        String productid = request.getParameter("productid");
        String number = request.getParameter("number");
        if(obj!=null && productid!=null && number!=null){
            ShoppingCar shoppingCar = (ShoppingCar) obj;
            shoppingCar.updateLine(Long.parseLong(productid), Integer.parseInt(number));
            response.sendRedirect("shopcart.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
