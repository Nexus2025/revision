package com.revision.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reverse = request.getParameter("set_reverse");

        if (reverse != null) {
            if (reverse.equals("on")) {
                HttpSession session = request.getSession();
                session.setAttribute("reverse", "ON");
                Cookie cookieReverse = new Cookie("reverse", "ON");
                cookieReverse.setMaxAge(86400);
                response.addCookie(cookieReverse);

            } else if (reverse.equals("off")) {
                HttpSession session = request.getSession();
                session.setAttribute("reverse", "OFF");
                Cookie cookieReverse = new Cookie("reverse", "OFF");
                cookieReverse.setMaxAge(86400);
                response.addCookie(cookieReverse);
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/main.jsp");
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
