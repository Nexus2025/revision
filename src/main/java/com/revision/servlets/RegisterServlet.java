package com.revision.servlets;

import com.revision.model.UserManager;
import com.revision.services.MailService;

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = "WEB-INF/pages/registration.jsp";

        String userLogin = request.getParameter("username");
        String userPassword = request.getParameter("password");
        String userConfirmPassword = request.getParameter("confirm_password");

        HttpSession session = request.getSession();

        if (userLogin == null || userConfirmPassword == null || userPassword == null) {
            path = "/WEB-INF/pages/registration.jsp";

            //SOME FIELD IS EMPTY
            System.out.println("SOME FIELD IS EMPTY");

        } else {

            if (userLogin.length() < 4 || userConfirmPassword.length() < 5 || userPassword.length() < 5) {
                path = "/WEB-INF/pages/registration.jsp";
                //LOGIN or PASSWORD are TOO SHORT
                System.out.println("LOGIN or PASSWORD are TOO SHORT");
                session.setAttribute("errorMessage", "some field is empty or too short");

            } else if (!userPassword.equals(userConfirmPassword)) {
                path = "/WEB-INF/pages/registration.jsp";
                //PASSWORD and CONFIRM PASSWORD are not the same
                System.out.println("PASSWORD and CONFIRM PASSWORD are not the same");
                session.setAttribute("errorMessage", "password and password confirmation are empty or not the same");

            } else {

                if (userLogin.contains(" ")) {
                    path = "/WEB-INF/pages/registration.jsp";
                    //LOGIN IS NOT VALID
                    System.out.println("LOGIN IS NOT VALID");
                    session.setAttribute("errorMessage", "login is not valid. do not use gaps");

                } else {
                    UserManager um = new UserManager();
                    boolean userLoginExist = um.checkUserExists(userLogin);

                    if (userLoginExist) {
                        path = "/WEB-INF/pages/registration.jsp";
                        //LOGIN IS BUSY
                        System.out.println("LOGIN IS BUSY");
                        session.setAttribute("errorMessage", "login is busy");

                    } else {
                        boolean result = um.createUser(userLogin, userPassword, 1);
                        if (result) {
                            path = "/login";
                            MailService.sendEmailToAdmin(userLogin);
                            //SUCCESS
                            System.out.println("SUCCESS");

                            session.setAttribute("userLogin", userLogin);
                            session.setAttribute("userPassword", userPassword);

                            response.sendRedirect(path);
                            return;

                        } else {
                            path = "";
                            //GO TO ERROR PAGE IF MYSQL ERROR
                            System.out.println("GO TO ERROR PAGE IF MYSQL ERROR");
                        }
                    }

                }
            }
        }

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
