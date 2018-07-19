package com.estore.web.controller;

import com.estore.common.exception.BookException;
import com.estore.entity.Book;
import com.estore.entity.Line;
import com.estore.entity.ShoppingCar;
import com.estore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "AddProductServlet", urlPatterns = "/addProduct")
@Controller
public class AddProductServlet extends HttpServlet {

    @Autowired
    private IBookService iBookService;

    @RequestMapping("/addProduct")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ShoppingCar shoppingCar;
        if(session.getAttribute("shopping_car")!=null){
            shoppingCar = (ShoppingCar)session.getAttribute("shopping_car");
        }else{
            shoppingCar = new ShoppingCar();
        }
        String bookId = request.getParameter("productid");
        int id = Integer.parseInt(bookId);
        try {
            Book book = iBookService.findById((long) id);
            Line line = new Line();
            line.setBook(book);
            shoppingCar.add(line);
        } catch (BookException e) {
            System.out.println(e.toString());
        }
        session.setAttribute("shopping_car", shoppingCar);
        response.sendRedirect("shopcart.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
