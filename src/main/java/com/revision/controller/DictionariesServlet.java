package com.revision.controller;

import com.revision.entity.Dictionary;
import com.revision.entity.User;
import com.revision.service.DictionaryManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dictionaries")
public class DictionariesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete_dictionary")) {
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                user.setDictionaryList(deleteDictionary(userId, dictionaryId));
                session.setAttribute("user", user);

                response.sendRedirect("/dictionaries");

            } else if (request.getParameter("action").equals("clear_dictionary")) {
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                user.setDictionaryList(clearDictionary(userId, dictionaryId));
                session.setAttribute("user", user);

                response.sendRedirect("/dictionaries");

            } else if (request.getParameter("action").equals("rename_dictionary")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                String dictionaryNewName = request.getParameter("dictionary_new_name");

                user.setDictionaryList(renameDictionary(dictionaryNewName, dictionaryId, userId));
                session.setAttribute("user", user);

                response.sendRedirect("/dictionaries");

            } else if (request.getParameter("action").equals("add_dictionary")) {

                String dictionaryName = request.getParameter("dictionary_name");

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                user.setDictionaryList(createDictionary(dictionaryName, userId));

                response.sendRedirect("/dictionaries");
            }


        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/dictionaries.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private List<Dictionary> deleteDictionary(int userId, int dictionaryId) {
        List<Dictionary> dictionaryList = new ArrayList<>();
        DictionaryManager dm = new DictionaryManager();
        dictionaryList = dm.deleteDictionary(userId, dictionaryId);

        return dictionaryList;
    }

    private List<Dictionary> renameDictionary(String dictionaryNewName, int dictionaryId, int userId) {
        List<Dictionary> dictionaryList = new ArrayList<>();
        DictionaryManager dm = new DictionaryManager();
        dictionaryList = dm.renameDictionary(dictionaryNewName, dictionaryId, userId);

        return dictionaryList;
    }

    private List<Dictionary> clearDictionary(int userId, int dictionaryId) {
        List<Dictionary> dictionaryList = new ArrayList<>();
        DictionaryManager dm = new DictionaryManager();
        dictionaryList = dm.clearDictionary(userId, dictionaryId);

        return dictionaryList;
    }

    private List<Dictionary> createDictionary (String dictionaryName, int userId) {
        List<Dictionary> dictionaryList = new ArrayList<>();
        DictionaryManager dm = new DictionaryManager();
        dictionaryList = dm.createDictionary(dictionaryName, userId);

        return dictionaryList;
    }
}
