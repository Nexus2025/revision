package com.revision.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/tech")
public class TechServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reverse = request.getParameter("set_reverse");

        if (reverse != null) {
            if (reverse.equals("on")) {
                HttpSession session = request.getSession();
                session.setAttribute("reverse", "ON");
            } else if (reverse.equals("off")) {
                HttpSession session = request.getSession();
                session.setAttribute("reverse", "OFF");
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/tech.jsp");
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
