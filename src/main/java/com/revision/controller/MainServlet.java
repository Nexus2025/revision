package com.revision.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reverse = request.getParameter("set_reverse");
        if (reverse != null) {
            HttpSession session = request.getSession();

            if (reverse.equals("on")) {
                session.setAttribute("reverse", "ON");
                Cookie cookieReverse = new Cookie("reverse", "ON");
                cookieReverse.setMaxAge(86400);
                response.addCookie(cookieReverse);

            } else if (reverse.equals("off")) {
                session.setAttribute("reverse", "OFF");
                Cookie cookieReverse = new Cookie("reverse", "OFF");
                cookieReverse.setMaxAge(86400);
                response.addCookie(cookieReverse);
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/main.jsp");
        requestDispatcher.forward(request, response);
    }
}
