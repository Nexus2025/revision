package com.revision.controller;

import com.revision.entity.User;
import com.revision.service.WordManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@WebServlet("/import")
@MultipartConfig
public class ImportServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("import_csv")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getUserId();

                int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

                Part filePart = request.getPart("csv_file");
                InputStream fileContent = filePart.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8"));

                WordManager wm = new WordManager();
                boolean result = wm.importWords(reader, dictionaryId, userId);

                System.out.println("reader: " + reader);

                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("reader: " + reader);
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/import.jsp");
        requestDispatcher.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
