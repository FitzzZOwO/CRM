package com.wenjing.crm.web.filter;

import com.wenjing.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("User");
        String path = request.getServletPath();
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            //登录相关资源放行
            chain.doFilter(req, resp);
        } else {
            if (user != null) {
                //有session，登陆过，放行

                chain.doFilter(req, resp);

            } else {
                //未登录，重定向
                response.sendRedirect("/login.jsp");
            }
        }

    }
}
