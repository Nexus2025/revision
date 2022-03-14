package com.revision.controller;

import com.revision.entity.User;
import com.revision.service.SectionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sections")
public class SectionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete_section")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                boolean result = deleteSection(sectionId, userId);
                response.sendRedirect("/sections?dictionary_id=" + dictionaryId);

            } else if (request.getParameter("action").equals("clear_section")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                boolean result = clearSection(sectionId, userId);
                response.sendRedirect("/sections?dictionary_id=" + dictionaryId);

            } else if (request.getParameter("action").equals("rename_section")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                String sectionNewName = request.getParameter("section_new_name");
                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                boolean result = renameSection(sectionNewName, sectionId, userId);
                response.sendRedirect("/sections?dictionary_id=" + dictionaryId);

            } else if (request.getParameter("action").equals("add_section")) {

                String sectionName = request.getParameter("section_name");

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                boolean result = createSection(sectionName, dictionaryId, userId);
                response.sendRedirect("/sections?dictionary_id=" + dictionaryId);
            }

        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/sections.jsp");
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

    private boolean deleteSection (int sectionId, int userId) {
        boolean result = false;
        SectionManager sm = new SectionManager();
        result = sm.deleteSection(sectionId, userId);

        return result;
    }

    private boolean clearSection (int sectionId, int userId) {
        boolean result = false;
        SectionManager sm = new SectionManager();
        result = sm.clearSection(sectionId, userId);

        return result;
    }

    private boolean renameSection (String sectionNewName, int sectionId, int userId) {
        boolean result = false;
        SectionManager sm = new SectionManager();
        result = sm.renameSection(sectionNewName, sectionId, userId);

        return  result;
    }

    private boolean createSection (String sectionName, int dictionaryId, int userId) {
        boolean result = false;
        SectionManager sm = new SectionManager();
        int sectionId = sm.createSection(sectionName, dictionaryId, userId);

        if (sectionId != -1) {
            result = true;
        }

        return  result;
    }
}
