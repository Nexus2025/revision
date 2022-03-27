package com.revision.google.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.revision.entity.Dictionary;
import com.revision.entity.User;
import com.revision.entity.Word;
import com.revision.google.entity.SheetTO;
import com.revision.google.service.GoogleDriveService;
import com.revision.google.service.GoogleSheetsService;
import com.revision.google.util.TokenUtil;
import com.revision.service.DictionaryService;
import com.revision.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@WebServlet("/import-sheets")
public class ImportSheetsServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ImportSheetsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Credential credential = (Credential) session.getAttribute("credential");
        User user = (User) session.getAttribute("user");

        if (TokenUtil.isTokenExpired(credential)) {
            logger.info("Token expired: {}", TokenUtil.isTokenExpired(credential));
            credential.setAccessToken(TokenUtil.refreshAccessToken((String)session.getAttribute("refreshToken")));
        }

        List<Dictionary> dictionaries = new DictionaryService().getAll(user.getId());
        request.setAttribute("dictionaries", dictionaries);

        List<SheetTO> sheets = new GoogleDriveService().getSheets(credential);
        request.setAttribute("sheets", sheets);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/pages/import-sheets.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String sheetId = request.getParameter("sheet_id");
        Integer dictionaryId = Integer.parseInt(request.getParameter("dictionary_id"));

        Credential credential = (Credential) session.getAttribute("credential");

        if (TokenUtil.isTokenExpired(credential)) {
            logger.info("Token expired: {}", TokenUtil.isTokenExpired(credential));
            credential.setAccessToken(TokenUtil.refreshAccessToken((String)session.getAttribute("refreshToken")));
        }

        List<Word> words = new GoogleSheetsService().getWords(user.getId(), dictionaryId, sheetId, credential);
        new WordService().saveList(words, user.getId(), dictionaryId);

        response.sendRedirect("/main");
    }
}
