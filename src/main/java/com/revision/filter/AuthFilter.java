package com.revision.filter;

import com.revision.entity.User;
import com.revision.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String destinationPath = request.getRequestURI();

        if(destinationPath.equals("/css/style.css")){
            filterChain.doFilter(request, response);
        }

        if (isAuth(request)) {
            if (destinationPath.equals("/login") || destinationPath.equals("/") || destinationPath.equals("/registration")) {
                response.sendRedirect("/main");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
             if (destinationPath.equals("/login") || destinationPath.equals("/registration")) {
                 filterChain.doFilter(request, response);
             } else {
                 response.sendRedirect("/login");
             }
        }
    }

    private boolean isAuth(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean isAuth = false;

        if(!session.isNew()){
            if (session.getAttribute("user") != null) {
                isAuth = true;
            }
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("u_id")) {
                        UserService userService = new UserService();
                        User user = userService.get(cookie.getValue());
                        session.setAttribute("user", user);
                        isAuth = true;
                    }
                    if (cookie.getName().equals("reverse")) {
                        String reverse = cookie.getValue();
                        session.setAttribute("reverse", reverse);
                    }
                }
            }
        }
        return isAuth;
    }
}
