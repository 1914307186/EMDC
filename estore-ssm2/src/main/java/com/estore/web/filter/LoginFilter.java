package com.estore.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/user/*","/confirmOrder"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if(req instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
            HttpServletResponse httpServletResponse = ((HttpServletResponse)resp);
            HttpSession session = httpServletRequest.getSession();
            Object customer = session.getAttribute("customer");
            if(customer==null){
                StringBuffer requestURL = httpServletRequest.getRequestURL();
                httpServletResponse.sendRedirect(((HttpServletRequest) req).getContextPath() + "/login.jsp?gotopage="+requestURL);
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
