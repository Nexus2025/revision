package com.revision.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/download-csv")
public class DownloadCsvServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/csv");

        File file;
        OutputStream out = null;
        BufferedInputStream bis = null;

        try {

            file = new File(this.getServletContext().getRealPath("/csv/example.csv"));
            byte[] content = new byte[900];

            out = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));

            while (bis.available() > 0) {
                bis.read(content);
            }

            response.setHeader("Content-Disposition", "attachment;filename=example.csv");
            out.write(content);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (out != null ) {
                out.close();
            }
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
}
