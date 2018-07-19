package com.estore.web.controller;

import com.estore.entity.*;
import com.estore.common.exception.CustomerException;
import com.estore.service.ICustomerService;
import com.estore.service.ICustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "RegisterServlet", value = "/register")
@Controller
public class RegisterServlet extends HttpServlet {

    @Autowired
    private  ICustomerService iCustomerService;

    @RequestMapping("/register")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Customer customer = new Customer();
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");
        String country = request.getParameter("country");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String street1 = request.getParameter("street1");
        String street2 = request.getParameter("street2");
        String address = country+ "-" +province+ "-" +city+ "-" +street1+ "-" +
                         country+ "-" +province+ "-" +city+ "-"+street2;
        String zip = request.getParameter("zip");
        String homephone = request.getParameter("homephone");
        String officephone = request.getParameter("officephone");
        String cellphone = request.getParameter("cellphone");
        String telephone = homephone+"-"+officephone+"-"+cellphone;
        String email = request.getParameter("email");
        if(name!=null && pwd!=null && !name.equals("") && !pwd.equals("")){
            customer.setName(name);
            customer.setPassword(pwd);
            customer.setAddress(address);
            customer.setZip(zip);
            customer.setTelephone(telephone);
            customer.setEmail(email);
            try {
                iCustomerService.register(customer);
                System.out.println("注册成功！");

                String gotopage = request.getParameter("gotopage");
                response.sendRedirect("login.jsp?gotopage="+gotopage);
            } catch (CustomerException e) {
                out.write("注册失败！");
                out.flush();
                System.out.println(e.toString());
            }
        }else {
            out.write("用户名或密码为空，注册失败!");
            out.flush();
        }
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init() throws ServletException {
    }
}
