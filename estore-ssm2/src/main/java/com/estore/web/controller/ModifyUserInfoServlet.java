package com.estore.web.controller;

import com.estore.common.exception.CustomerException;
import com.estore.entity.Customer;
import com.estore.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "ModifyUserInfoServlet", urlPatterns = "/user/modify")
@Controller
public class ModifyUserInfoServlet extends HttpServlet {

    @Autowired
    private ICustomerService iCustomerService;

    @RequestMapping("/user/modify")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
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
        if(name!=null && pwd!=null && !pwd.equals("") && !name.equals("")){
            customer.setName(name);
            customer.setPassword(pwd);
            customer.setAddress(address);
            customer.setZip(zip);
            customer.setTelephone(telephone);
            customer.setEmail(email);
            try {
                iCustomerService.updateCustomer(customer);
                System.out.println("修改成功！");
                session.setAttribute("customer", customer);
                session.setAttribute("country",country);
                session.setAttribute("province",province);
                session.setAttribute("city",city);
                session.setAttribute("street1",street1);
                session.setAttribute("street2",street2);
                session.setAttribute("homephone",homephone);
                session.setAttribute("officephone",officephone);
                session.setAttribute("cellphone",cellphone);
                response.sendRedirect("userinfo.jsp");
            } catch (CustomerException e) {
                System.out.println(e.toString());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
