package com.revision.controller.repeating;

import com.revision.entity.Dictionary;
import com.revision.entity.Section;
import com.revision.entity.User;
import com.revision.service.DictionaryService;
import com.revision.service.SectionService;
import com.revision.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/change")
public class ChangeRepeatingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        List<Dictionary> dictionaryList = new DictionaryService().getAllContainWords(user.getId());
        List<Section> sectionList = new SectionService().getAllContainWords(user.getId());
        int countOfAllWords = new UserService().getCountOfWords(user.getId());

        request.setAttribute("dictionaryList", dictionaryList);
        request.setAttribute("sectionList", sectionList);
        request.setAttribute("countOfAllWords", countOfAllWords);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/change-repeat.jsp");
        requestDispatcher.forward(request, response);
    }
}
