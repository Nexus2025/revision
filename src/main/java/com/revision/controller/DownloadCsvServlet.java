package com.revision.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/download-csv")
public class DownloadCsvServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DownloadCsvServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=example.csv");

        File file = new File(this.getServletContext().getRealPath("/csv/example.csv"));
        try (OutputStream out = response.getOutputStream();
             BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] content = new byte[900];
            while (bis.available() > 0) {
                bis.read(content);
            }
            out.write(content);
        } catch (IOException e) {
            log.error(e.getMessage());
            response.sendRedirect("/import");
        }
    }
}
