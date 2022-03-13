package com.revision.filters;

import com.revision.entities.User;
import com.revision.model.UserManager;

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
                        UserManager userService = new UserManager();
                        User user = userService.userRead(cookie.getValue());
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
