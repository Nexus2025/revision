package com.revision.controller.auth;

import com.revision.entity.Role;
import com.revision.service.UserService;
import com.revision.util.MailService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registration")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/registration.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        HttpSession session = request.getSession();

        if (validateParameters(username, password, confirmPassword, session)) {
            if(register(username, password, session)) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
                requestDispatcher.forward(request, response);
                return;
            }
        }
        response.sendRedirect("/registration");
    }

    private boolean validateParameters(String username, String password, String confirmPassword, HttpSession session) {
        if (username.length() < 4 || confirmPassword.length() < 5 || password.length() < 5) {
            session.setAttribute("errorMessage", "some field is empty or too short");
            return false;
        } else if (!password.equals(confirmPassword)) {
            session.setAttribute("errorMessage", "password and password confirmation are empty or not the same");
            return false;
        } else if (username.contains(" ") || password.contains(" ")) {
            session.setAttribute("errorMessage", "login or password are not valid. do not use gaps");
            return false;
        }
        return true;
    }

    private boolean register(String username, String password, HttpSession session) {

        UserService userService = new UserService();
        if (userService.checkExists(username)) {
            session.setAttribute("errorMessage", "login is busy");
            return false;
        } else {
            userService.create(username, password, Role.USER);
            MailService.sendEmailToAdmin(username);
            return true;
        }
    }
}
