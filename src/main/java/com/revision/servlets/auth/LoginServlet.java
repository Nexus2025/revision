package com.revision.servlets.auth;

import com.revision.entities.User;
import com.revision.util.Encryptor;
import com.revision.model.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/auth.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (validateParameters(username, password, session)) {
            authorize(username, password, session, response);
        }
        response.sendRedirect("/login");
    }

    private boolean validateParameters(String username, String password, HttpSession session) {
        if (username.length() < 4 || password.length() < 5) {
            session.setAttribute("errorMessage", "login or password are empty or too short");
            return false;
        }
        return true;
    }

    private void authorize(String username, String password, HttpSession session, HttpServletResponse response) {
        UserManager userService = new UserManager();
        User user = userService.userRead(username);
        try {
            if (user != null) {
                if (user.getLogin().equals(username) && user.getPassword().equals(Encryptor.encrypt(password))) {
                    Cookie cookieUid = new Cookie("u_id", user.getLogin());
                    cookieUid.setMaxAge(86400);
                    Cookie cookieReverse = new Cookie("reverse", "ON");
                    cookieReverse.setMaxAge(86400);

                    response.addCookie(cookieUid);
                    response.addCookie(cookieReverse);

                    session.setAttribute("user", user);
                    session.setAttribute("reverse", "ON");
                    return;
                }
            }
        } catch (GeneralSecurityException e) {
            session.setAttribute("errorMessage", "error has occurred. try again later");
        }
        session.setAttribute("errorMessage", "login or password are incorrect");
    }
}
