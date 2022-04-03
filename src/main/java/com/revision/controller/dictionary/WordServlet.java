package com.revision.controller.dictionary;

import com.revision.entity.User;
import com.revision.entity.Word;
import com.revision.service.WordService;
import com.revision.util.ImportUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/words")
public class WordServlet extends HttpServlet {

    private final WordService wordService = new WordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (checkParamsAreValid(request)) {
            int sectionId = Integer.parseInt(request.getParameter("section_id"));
            List<Word> wordList = wordService.getAllBySectionId(sectionId, user.getId());
            request.setAttribute("wordList", wordList);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/words.jsp");
            requestDispatcher.forward(request, response);

        } else {
            response.sendRedirect("/dictionaries");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int sectionId = Integer.parseInt(request.getParameter("section_id"));
        int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete_word")) {
                int userId = user.getId();
                int wordId = Integer.parseInt(request.getParameter("word_id"));
                wordService.delete(wordId, userId);

            } else if (request.getParameter("action").equals("rename_word")) {
                int userId = user.getId();
                int wordId = Integer.parseInt(request.getParameter("word_id"));
                String wordNew = request.getParameter("word_new");
                String translationNew = request.getParameter("translation_new");
                String wordOld = request.getParameter("word_old");
                String translationOld = request.getParameter("translation_old");
                wordService.rename(checkWordValid(wordNew, wordOld)
                        , checkWordValid(translationNew, translationOld), wordId, userId);

            } else if (request.getParameter("action").equals("add_word")) {
                int userId = user.getId();
                String word = request.getParameter("word_add_name");
                String translation = request.getParameter("translation_add_name");
                wordService.create(word, translation, sectionId, userId, dictionaryId);

            } else if (request.getParameter("action").equals("delete_all_words")) {
                int userId = user.getId();
                wordService.deleteAllBySectionId(sectionId, userId);

            } else if (request.getParameter("action").equals("import_words")) {
                int userId = user.getId();
                String wordsLine = request.getParameter("words_line");
                ImportUtil.importWordsFromForm(wordsLine, sectionId, userId, dictionaryId);
            }
        }

        response.sendRedirect("/words?dictionary_id=" + dictionaryId + "&section_id=" + sectionId);
    }

    private String checkWordValid (String newWord, String oldWord) {
        return newWord.isEmpty() ? oldWord : newWord;
    }

    private boolean checkParamsAreValid(HttpServletRequest request) {
        try {
            Integer.parseInt(request.getParameter("dictionary_id"));
            Integer.parseInt(request.getParameter("section_id"));
            return true;
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
    }
}
