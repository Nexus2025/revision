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
import java.util.TreeMap;

@WebServlet("/repeating")
public class RepeatServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("start-repeating").equals("start")) {

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int userId = user.getId();

            String pathReturn = request.getParameter("path_return");
            if (pathReturn != null) {
                session.setAttribute("pathReturn", pathReturn);
            }

            if (request.getParameter("repeat_by").equals("dictionary")) {

                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                WordService wm = new WordService();

                List<Word> wordList = wm.getAllByDictionaryId(dictionaryId, userId);

                int countOfAllWords = wordList.size();

                String reverse = (String) session.getAttribute("reverse");
                if (reverse.equals("ON")) {
                    swapWords(wordList);
                }

                Map<Integer,Word> wordMap = new TreeMap<>();

                int maxId = 0;

                for (Word word : wordList) {
                    wordMap.put(word.getId(), word);
                    if (maxId < word.getId()) {
                        maxId = word.getId();
                    }
                }

                int firstWord = wordList.get(0).getId();

                DictionaryService dm = new DictionaryService();
                Dictionary dictionary = dm.get(userId, dictionaryId);
                String dictionaryName = dictionary.getName();

                session.setAttribute("firstWordId", firstWord);
                session.setAttribute("wordMap", wordMap);
                session.setAttribute("maxId", maxId);
                session.setAttribute("rightAnswers",0);
                session.setAttribute("wrongAnswers",0);
                session.setAttribute("repeatBy", "dictionary");
                session.setAttribute("id", dictionaryId);
                session.setAttribute("countOfAllWords", countOfAllWords);
                session.setAttribute("nameOfTargetList", dictionaryName);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/repeating.jsp");
                requestDispatcher.forward(request, response);

            }

            if (request.getParameter("repeat_by").equals("section")) {

                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                WordService wm = new WordService();

                List<Word> wordList = wm.getAllBySectionId(sectionId, userId);

                int countOfAllWords = wordList.size();

                String reverse = (String) session.getAttribute("reverse");
                if (reverse.equals("ON")) {
                    swapWords(wordList);
                }

                Map<Integer,Word> wordMap = new TreeMap<>();

                int maxId = 0;

                for (Word word : wordList) {
                    wordMap.put(word.getId(), word);
                    if (maxId < word.getId()) {
                        maxId = word.getId();
                    }
                }

                for (Word word : wordList) {
                    wordMap.put(word.getId(), word);
                }

                int firstWord = wordList.get(0).getId();

                SectionService sm = new SectionService();
                Section section = sm.get(userId, sectionId);
                String sectionName = section.getName();

                session.setAttribute("firstWordId", firstWord);
                session.setAttribute("wordMap", wordMap);
                session.setAttribute("maxId", maxId);
                session.setAttribute("rightAnswers",0);
                session.setAttribute("wrongAnswers",0);
                session.setAttribute("repeatBy", "section");
                session.setAttribute("id", sectionId);
                session.setAttribute("countOfAllWords", countOfAllWords);
                session.setAttribute("nameOfTargetList", sectionName);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/repeating.jsp");
                requestDispatcher.forward(request, response);

            }

        } else {
            response.sendRedirect("/change");
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

    private void swapWords (List<Word> wordList) {

        for (Word word : wordList) {
            String tempWord = word.getWord();
            String tempTranslation = word.getTranslation();

            word.setWord(tempTranslation);
            word.setTranslation(tempWord);

        }
    }
}
