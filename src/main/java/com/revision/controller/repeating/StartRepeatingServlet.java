package com.revision.controller.repeating;

import com.revision.entity.Dictionary;
import com.revision.entity.Section;
import com.revision.entity.User;
import com.revision.entity.Word;
import com.revision.service.DictionaryService;
import com.revision.service.SectionService;
import com.revision.service.WordService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/repeating")
public class StartRepeatingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("start-repeating").equals("start")) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int userId = user.getId();

            WordService wordService = new WordService();
            List<Word> wordList = null;
            if (request.getParameter("repeat_by").equals("dictionary")) {
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                wordList = wordService.getAllByDictionaryId(dictionaryId, userId);

                DictionaryService dictionaryService = new DictionaryService();
                Dictionary dictionary = dictionaryService.get(userId, dictionaryId);
                session.setAttribute("id", dictionaryId);
                session.setAttribute("nameOfTargetList", dictionary.getName());
            }
            else if (request.getParameter("repeat_by").equals("section")) {
                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                wordList = wordService.getAllBySectionId(sectionId, userId);

                SectionService sectionService = new SectionService();
                Section section = sectionService.get(userId, sectionId);
                session.setAttribute("id", sectionId);
                session.setAttribute("nameOfTargetList", section.getName());
            }

            String reverse = (String) session.getAttribute("reverse");
            if (reverse.equals("ON")) {
                swapWords(wordList);
            }

            Map<Integer,Word> wordMap = wordList
                    .stream()
                    .collect(Collectors.toMap(Word::getId, word -> word));

            session.setAttribute("maxId", wordMap.keySet().stream().max(Integer::compare).get());
            session.setAttribute("firstWordId", wordMap.keySet().iterator().next());
            session.setAttribute("wordMap", wordMap);
            session.setAttribute("rightAnswers",0);
            session.setAttribute("wrongAnswers",0);
            session.setAttribute("repeatBy", "dictionary");
            session.setAttribute("countOfAllWords", wordMap.size());
            session.setAttribute("pathReturn", request.getParameter("path_return"));

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/repeating.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("/change");
        }
    }

    private void swapWords (List<Word> wordList) {
        for (Word word : wordList) {
            String tmp = word.getWord();
            word.setWord(word.getTranslation());
            word.setTranslation(tmp);
        }
    }
}
