package com.revision.filters;

import com.revision.entities.User;
import com.revision.model.UserManager;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        boolean isAuth = checkAuth(request);
        String currentPath = request.getRequestURI();
        String path = "";

        System.out.println("AuthFilter.Current URL: " + request.getRequestURI());
        System.out.println("AuthFilter.isAuth: " + isAuth);

        if (isAuth) {
            if (currentPath.equals("/login") || currentPath.equals("/") || currentPath.equals("/registration")) {
                path = "/main";
            } else {
                path = currentPath;
            }

        } else {
             if (currentPath.equals("/login")) {
                 path = "/login";
             } else if (currentPath.equals("/registration")) {
                 path = "/registration";
             }
             else {
                 path = "WEB-INF/pages/auth.jsp";
             }
        }

        System.out.println("AuthFilter.Result URL = " + path);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    private boolean checkAuth (HttpServletRequest request) {

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
                        UserManager um = new UserManager();
                        User user = um.userRead(cookie.getValue());
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
