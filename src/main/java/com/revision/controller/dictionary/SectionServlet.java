package com.revision.controller.dictionary;

import com.revision.entity.Section;
import com.revision.entity.User;
import com.revision.service.SectionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/sections")
public class SectionServlet extends HttpServlet {

    private final SectionService sectionService = new SectionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
        List<Section> sectionList = new SectionService().getAllByDictionaryId(dictionaryId, user.getId());
        request.setAttribute("sectionList", sectionList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/sections.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete_section")) {
                int userId = user.getId();
                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                sectionService.delete(sectionId, userId);

            } else if (request.getParameter("action").equals("clear_section")) {
                int userId = user.getId();
                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                sectionService.clear(sectionId, userId);

            } else if (request.getParameter("action").equals("rename_section")) {
                int userId = user.getId();
                String sectionNewName = request.getParameter("section_new_name");
                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                sectionService.rename(sectionNewName, sectionId, userId);

            } else if (request.getParameter("action").equals("add_section")) {
                String sectionName = request.getParameter("section_name");
                int userId = user.getId();
                sectionService.create(sectionName, dictionaryId, userId);
            }

            response.sendRedirect("/sections?dictionary_id=" + dictionaryId);
        }
    }
}
