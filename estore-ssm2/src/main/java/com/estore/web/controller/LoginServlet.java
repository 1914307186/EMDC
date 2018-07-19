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
import java.io.PrintWriter;

//@WebServlet(name = "LoginServlet", urlPatterns = "/login", initParams = {
//        @WebInitParam(name = "date", value = "7-6"), @WebInitParam(name = "time", value = "15:44")})
@Controller
public class LoginServlet extends HttpServlet {

    @Autowired
    private ICustomerService iCustomerService;

    @Override
    public void init() throws ServletException {
        String date = getServletConfig().getInitParameter("date");
        String time = getServletConfig().getInitParameter("time");
        System.out.println(date+" "+time);
    }

    @RequestMapping("/login")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = new Customer();
        PrintWriter out = response.getWriter();
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        if(name!=null && password!=null && !name.equals("") && !password.equals("")){
            customer.setName(name);
            customer.setPassword(password);
            
            try {
                customer = iCustomerService.login(name, password);
                if(customer!=null){
                    session.setAttribute("customer", customer);

                    String[] split = customer.getAddress().split("[-]");
                    String country = split[0];
                    String province = split[1];
                    String city = split[2];
                    String street1 = split[3];
                    String street2 = split[7];

                    session.setAttribute("country",country);
                    session.setAttribute("province",province);
                    session.setAttribute("city",city);
                    session.setAttribute("street1",street1);
                    session.setAttribute("street2",street2);

                    String[] split1 = customer.getTelephone().split("[-]");
                    String homephone = split1[0];
                    String officephone = split1[1];
                    String cellphone = split1[2];
                    session.setAttribute("homephone",homephone);
                    session.setAttribute("officephone",officephone);
                    session.setAttribute("cellphone",cellphone);

                    System.out.println("登录成功！");
                    String gotopage = request.getParameter("gotopage");
                    if(gotopage!=null && !gotopage.equals("")){
                        response.sendRedirect(gotopage);
                    }else {
                        response.sendRedirect("index.jsp");
                    }
//                    out.write("登录成功！");
//                    out.flush();
                }else {
                    out.write("用户名或密码错误，登录失败！");
                    out.flush();
                }
            } catch (CustomerException e) {
                out.write("用户名或密码错误，登录失败！");
                out.flush();
                System.out.println(e.toString());

            }

        }else {
            out.write("登录失败，用户名或密码为空！");
            out.flush();
        }
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}
