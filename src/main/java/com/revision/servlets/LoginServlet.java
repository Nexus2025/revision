package com.revision.servlets;

import com.revision.entities.User;
import com.revision.model.Encryptor;
import com.revision.model.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("LoginServlet");

        HttpSession session = request.getSession();
        Object userLogin = session.getAttribute("userLogin");
        Object userPassword = session.getAttribute("userPassword");

        String loginParameter;
        String passwordParameter;
        String path;


        if (userLogin != null && userPassword != null) {

            loginParameter = (String) userLogin;
            passwordParameter = (String) userPassword;
            path = "";

        } else {
            loginParameter = request.getParameter("username");
            passwordParameter = request.getParameter("password");
            path = "";
        }

        if (loginParameter == null || passwordParameter == null) {
            path = "WEB-INF/pages/auth.jsp";
            //LOGIN OR PASSWORD IS NULL
        }

        else if (loginParameter.length() < 4 || passwordParameter.length() < 5) {
            path = "WEB-INF/pages/auth.jsp";
            //LOGIN OR PASSWORD TOO SHORT
            session.setAttribute("errorMessage", "login or password are empty or too short");

        } else {
            UserManager um = new UserManager();
            User user = um.userRead(loginParameter);

            if (user != null) {
                Encryptor pe = new Encryptor();
                if (user.getLogin().equals(loginParameter) && user.getPassword().equals(pe.encrypt(passwordParameter))) {
                    HttpSession httpSession = request.getSession();

                    Cookie cookieUid = new Cookie("u_id", user.getLogin());
                    cookieUid.setMaxAge(86400);

                    Cookie cookieReverse = new Cookie("reverse", "ON");
                    cookieReverse.setMaxAge(86400);

                    httpSession.setAttribute("user", user);
                    httpSession.setAttribute("reverse", "ON");

                    response.addCookie(cookieUid);
                    response.addCookie(cookieReverse);
                    response.sendRedirect("/main");
                    return;

//                    path = "WEB-INF/pages/main.jsp";

                } else {
                    path = "WEB-INF/pages/auth.jsp";
                    //LOGIN OR PASSWORD ARE INCORRECT
                    session.setAttribute("errorMessage", "login or password are incorrect");
                }

            } else {
                path = "WEB-INF/pages/auth.jsp";
                //LOGIN OR PASSWORD ARE INCORRECT
                session.setAttribute("errorMessage", "login or password are incorrect");
            }
        }

        System.out.println("LoginServlet End");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
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
