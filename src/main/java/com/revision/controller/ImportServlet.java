package com.revision.controller;

import com.revision.entity.Dictionary;
import com.revision.entity.User;
import com.revision.service.DictionaryService;
import com.revision.util.ImportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/import")
@MultipartConfig
public class ImportServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ImportServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Dictionary> dictionaries = new DictionaryService().getAll(user.getId());
        request.setAttribute("dictionaries", dictionaries);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/import.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int userId = user.getId();
            int dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

            Part filePart = request.getPart("csv_file");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
                ImportUtil.importWordsFromCSV(reader, dictionaryId, userId);
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        } catch (NullPointerException | NumberFormatException e) {
            log.error(e.getMessage());
        } finally {
            response.sendRedirect("/main");
        }
    }
}
