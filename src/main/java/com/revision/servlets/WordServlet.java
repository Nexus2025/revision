package com.revision.servlets;

import com.revision.entities.User;
import com.revision.model.WordManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/words")
public class WordServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete_word")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int wordId = Integer.parseInt(request.getParameter("word_id"));
                String sectionId = request.getParameter("section_id");
                String dictionaryId = request.getParameter("dictionary_id");

                boolean result = deleteWord(wordId, userId);

                response.sendRedirect("/words?dictionary_id=" + dictionaryId + "&section_id=" + sectionId);


            } else if (request.getParameter("action").equals("rename_word")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int wordId = Integer.parseInt(request.getParameter("word_id"));
                String sectionId = request.getParameter("section_id");
                String dictionaryId = request.getParameter("dictionary_id");

                String wordNew = request.getParameter("word_new");
                String translationNew = request.getParameter("translation_new");
                String wordOld = request.getParameter("word_old");
                String translationOld = request.getParameter("translation_old");

                String word = checkWordValid(wordNew, wordOld);
                String translation = checkWordValid(translationNew, translationOld);

                boolean result = renameWord(word, translation, wordId, userId);

                response.sendRedirect("/words?dictionary_id=" + dictionaryId + "&section_id=" + sectionId);

            } else if (request.getParameter("action").equals("add_word")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));
                String word = request.getParameter("word_add_name");
                String translation = request.getParameter("translation_add_name");

                boolean result = createWord(word, translation, sectionId, userId, dictionaryId);

                response.sendRedirect("/words?dictionary_id=" + dictionaryId + "&section_id=" + sectionId);

            } else if (request.getParameter("action").equals("delete_all_words")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                String dictionaryId = request.getParameter("dictionary_id");

                boolean result = deleteAllWordsById(sectionId,userId);

                response.sendRedirect("/words?dictionary_id=" + dictionaryId + "&section_id=" + sectionId);

            } else if (request.getParameter("action").equals("import_words")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                String wordString = request.getParameter("words_line");

                int sectionId = Integer.parseInt(request.getParameter("section_id"));
                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                boolean result = importWords(wordString, sectionId, userId, dictionaryId);

                response.sendRedirect("/words?dictionary_id=" + dictionaryId + "&section_id=" + sectionId);
            }

        } else {

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/words.jsp");
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

    private boolean deleteWord (int wordId, int userId) {
        boolean result = false;
        WordManager wm = new WordManager();
        result = wm.deleteWord(wordId, userId);

        return result;
    }

    private boolean createWord (String word, String translation, int sectionId, int userId, int dictionaryId) {
        boolean result = false;
        WordManager wm = new WordManager();
        result = wm.createWord(word, translation, sectionId, userId, dictionaryId);

        return result;
    }

    private boolean renameWord (String word, String translation, int wordId, int userId) {
        boolean result = false;
        WordManager wm = new WordManager();
        result = wm.renameWord(word, translation, wordId, userId);

        return result;
    }

    private boolean deleteAllWordsById (int sectionId, int userId) {
        boolean result = false;
        WordManager wm = new WordManager();
        result = wm.deleteAllWordsById(sectionId, userId);

        return result;
    }

    private boolean importWords (String wordString, int sectionId, int userId, int dictionaryId) {
        boolean result = false;
        WordManager wm = new WordManager();
        result = wm.writeWordsLineFromForm(wordString, sectionId, userId, dictionaryId);

        return result;
    }

    private String checkWordValid (String wordNew, String wordOld) {
        String word = wordNew;

        if (wordNew.equals("")) {
            word = wordOld;
        }

        return word;
    }
}
