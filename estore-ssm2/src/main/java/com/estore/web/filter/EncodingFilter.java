package com.estore.web.filter;

import javax.servlet.*;
import java.io.IOException;

@javax.servlet.annotation.WebFilter(filterName = "EncodingFilter", urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class EncodingFilter implements Filter {
    private FilterConfig config;

    public void destroy() {
        System.out.println(config.getFilterName()+" destroy...");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) {
        this.config = config;
        System.out.println(config.getFilterName()+" init...");
    }

}
