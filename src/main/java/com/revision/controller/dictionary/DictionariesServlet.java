package com.revision.controller.dictionary;

import com.revision.entity.Dictionary;
import com.revision.entity.User;
import com.revision.service.DictionaryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dictionaries")
public class DictionariesServlet extends HttpServlet {

    private final DictionaryService dictionaryService = new DictionaryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Dictionary> dictionaryList = dictionaryService.getAll(user.getId());
        request.setAttribute("dictionaryList", dictionaryList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/dictionaries.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete_dictionary")) {
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                int userId = user.getId();
                dictionaryService.delete(userId, dictionaryId);

            } else if (request.getParameter("action").equals("clear_dictionary")) {
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                int userId = user.getId();
                dictionaryService.clear(userId, dictionaryId);

            } else if (request.getParameter("action").equals("rename_dictionary")) {
                int userId = user.getId();
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                String dictionaryNewName = request.getParameter("dictionary_new_name");
                dictionaryService.rename(dictionaryNewName, dictionaryId, userId);

            } else if (request.getParameter("action").equals("add_dictionary")) {
                String dictionaryName = request.getParameter("dictionary_name");
                int userId = user.getId();
                dictionaryService.create(dictionaryName, userId);
            }
        }

        response.sendRedirect("/dictionaries");
    }
}
