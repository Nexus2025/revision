package com.revision.google.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.revision.google.entity.SheetTO;
import com.revision.google.service.GoogleDriveService;
import com.revision.google.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Credential credential = (Credential) session.getAttribute("credential");

        if (TokenUtil.isTokenExpired(credential)) {
            logger.info("Token expired: {}", TokenUtil.isTokenExpired(credential));
            credential.setAccessToken(TokenUtil.refreshAccessToken((String)session.getAttribute("refreshToken")));
        }

        List<SheetTO> sheets = new GoogleDriveService().getSheets(credential);
        sheets.forEach(System.out::println);

        SheetTO sheet = sheets.get(1);
    }
}
