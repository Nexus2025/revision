package com.revision.controller.repeating;

import com.revision.entity.Word;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@WebServlet("/process")
public class ProcessRepeatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        if (request.getParameter("action").equals("get_first_word")) {

            HttpSession session = request.getSession();
            Map<Integer, Word> wordMap = (Map<Integer, Word>) session.getAttribute("wordMap");

            int firstWordId = (Integer) session.getAttribute("firstWordId");
            session.removeAttribute("firstWordId");
            session.setAttribute("currentWordId", firstWordId);

            String word = wordMap.get(firstWordId).getWord() + ";?";

            response.setContentType("text/plain");

            OutputStream outStream = response.getOutputStream();
            outStream.write(word.getBytes("UTF-8"));
            outStream.flush();
            outStream.close();

        }

        if (request.getParameter("action").equals("get_two_words")) {

            HttpSession session = request.getSession();
            Map<Integer, Word> wordMap = (Map<Integer, Word>) session.getAttribute("wordMap");

            int currentWordId = (Integer)(session.getAttribute("currentWordId"));

            String word = wordMap.get(currentWordId).getWord();
            String translation = wordMap.get(currentWordId).getTranslation();
            String result = word + ";" + translation;

            response.setContentType("text/plain");

            OutputStream outStream = response.getOutputStream();
            outStream.write(result.getBytes("UTF-8"));
            outStream.flush();
            outStream.close();

        }


        if (request.getParameter("action").equals("right_next_word")) {

            HttpSession session = request.getSession();
            Map<Integer, Word> wordMap = (Map<Integer, Word>) session.getAttribute("wordMap");

            int currentWordId = (Integer)(session.getAttribute("currentWordId"));
            String word = "";

            wordMap.remove(currentWordId);
            int rightAnswers =  (Integer) session.getAttribute("rightAnswers");
            session.setAttribute("rightAnswers",rightAnswers + 1);

            if (wordMap.isEmpty()) {
                System.out.println("wordMap is empty");

                session.removeAttribute("wordMap");
                session.removeAttribute("maxId");

                String repeatBy = (String) session.getAttribute("repeatBy");
                int id = (Integer) session.getAttribute("id");
                String pathEnd = "";

                if (repeatBy.equals("dictionary")) {
                    pathEnd = "?repeat_by=dictionary&dictionary_id=" + id + "";
                } else {
                    pathEnd = "?repeat_by=section&section_id=" + id + "";
                }

                String serverName = request.getServerName();
                int serverPort = request.getServerPort();

                String path = "redirect300;http://" + serverName +":" + serverPort + "/result" + pathEnd;

                OutputStream outStream = response.getOutputStream();
                outStream.write(path.getBytes("UTF-8"));
                outStream.flush();
                outStream.close();

            } else {
                System.out.println("wordMap is not empty");

                for(Map.Entry<Integer, Word> entry : wordMap.entrySet()) {
                    currentWordId = entry.getKey();
                    word = entry.getValue().getWord() + ";?";
                    break;
                }

                session.setAttribute("currentWordId", currentWordId);

                response.setContentType("text/plain");

                OutputStream outStream = response.getOutputStream();
                outStream.write(word.getBytes("UTF-8"));
                outStream.flush();
                outStream.close();
            }
        }



        if (request.getParameter("action").equals("wrong_next_word")) {

            HttpSession session = request.getSession();
            Map<Integer, Word> wordMap = (Map<Integer, Word>) session.getAttribute("wordMap");

            int currentWordId = (Integer) session.getAttribute("currentWordId");
            int maxId = (Integer) session.getAttribute("maxId");

            int wrongAnswers = (Integer) session.getAttribute("wrongAnswers");
            session.setAttribute("wrongAnswers",wrongAnswers + 1);

            Word changeWord = wordMap.get(currentWordId);
            wordMap.remove(currentWordId);

            wordMap.put(maxId + 1, changeWord);
            session.setAttribute("maxId", maxId + 1);

            System.out.println("maxId : " + maxId);
            System.out.println(changeWord.getWord());

            String word = "";

            for(Map.Entry<Integer, Word> entry : wordMap.entrySet()) {
                currentWordId = entry.getKey();
                word = entry.getValue().getWord() + ";?";
                break;
            }

            session.setAttribute("currentWordId", currentWordId);

            response.setContentType("text/plain");

            OutputStream outStream = response.getOutputStream();
            outStream.write(word.getBytes("UTF-8"));
            outStream.flush();
            outStream.close();

        }

    }
}
