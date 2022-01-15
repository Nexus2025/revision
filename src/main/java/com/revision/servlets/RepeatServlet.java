package com.revision.servlets;

import com.revision.entities.Dictionary;
import com.revision.entities.Section;
import com.revision.entities.User;
import com.revision.entities.Word;
import com.revision.model.DictionaryManager;
import com.revision.model.SectionManager;
import com.revision.model.WordManager;

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
            int userId = user.getUserId();

            String pathReturn = request.getParameter("path_return");
            if (pathReturn != null) {
                session.setAttribute("pathReturn", pathReturn);
            }

            if (request.getParameter("repeat_by").equals("dictionary")) {

                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                WordManager wm = new WordManager();

                List<Word> wordList = wm.getWordListByDictionaryId(dictionaryId, userId);

                int countOfAllWords = wordList.size();

                String reverse = (String) session.getAttribute("reverse");
                if (reverse.equals("ON")) {
                    swapWords(wordList);
                }

                Map<Integer,Word> wordMap = new TreeMap<>();

                int maxId = 0;

                for (Word word : wordList) {
                    wordMap.put(word.getWordId(), word);
                    if (maxId < word.getWordId()) {
                        maxId = word.getWordId();
                    }
                }

                int firstWord = wordList.get(0).getWordId();

                DictionaryManager dm = new DictionaryManager();
                Dictionary dictionary = dm.getDictionaryById(userId, dictionaryId);
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
                WordManager wm = new WordManager();

                List<Word> wordList = wm.getWordListBySectionId(sectionId, userId);

                int countOfAllWords = wordList.size();

                String reverse = (String) session.getAttribute("reverse");
                if (reverse.equals("ON")) {
                    swapWords(wordList);
                }

                Map<Integer,Word> wordMap = new TreeMap<>();

                int maxId = 0;

                for (Word word : wordList) {
                    wordMap.put(word.getWordId(), word);
                    if (maxId < word.getWordId()) {
                        maxId = word.getWordId();
                    }
                }

                for (Word word : wordList) {
                    wordMap.put(word.getWordId(), word);
                }

                int firstWord = wordList.get(0).getWordId();

                SectionManager sm = new SectionManager();
                Section section = sm.getSectionById(userId, sectionId);
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
