package com.revision.servlets;

import com.revision.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        user = null;
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("u_id")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }

                    if (cookie.getName().equals("reverse")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/auth.jsp");
        requestDispatcher.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
