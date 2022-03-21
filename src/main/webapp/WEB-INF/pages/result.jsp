<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Revision</title>
    <style>
        <%@include file="/css/style.css"%>
    </style>
</head>
<body>
<div id="header">
    <h1 class="brand">Revision</h1>
    <p class="logout"><a class="logout-link" href="/logout">Logout</a></p>
    <div class="clear-left"></div>
</div>
<div id="container">
    <div id="sidebar">
        <div id="sidebar-top"></div>
        <div>
            <p><input class="button-sidebar" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="button-sidebar" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="button-sidebar" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
        </div>
    </div>
    <div id="content">
        <div>
            <h2>RESULTS</h2>
            <%
                String repeatBy = (String) session.getAttribute("repeatBy");
                int id = (int) session.getAttribute("id");
                String pathAgain = repeatBy.equals("dictionary") ? "dictionary_id=" + id : "section_id=" + id;
            %>
            <div class="indent">
                <p class="repeating-results-dashboard" style="color: #f44e28">${sessionScope.get('nameOfTargetList')}</p>
                <p class="repeating-results-dashboard">RIGHT ANSWERS ${sessionScope.get('rightAnswers')}</p>
                <p class="repeating-results-dashboard">WRONG ANSWERS ${sessionScope.get('wrongAnswers')}</p>
                <p><input class="add-button" type="submit" value="REPEAT AGAIN"
                          onclick="location.href='/repeating?start-repeating=start&repeat_by=<%=repeatBy%>&<%=pathAgain%>'">
                    <input class="add-button back-button" type="submit" value="BACK" onclick="location.href='${sessionScope.get('pathReturn')}'">
                </p>
                <%
                    session.removeAttribute("rightAnswers");
                    session.removeAttribute("wrongAnswers");
                    session.removeAttribute("repeatBy");
                    session.removeAttribute("id");
                    session.removeAttribute("countOfAllWords");
                    session.removeAttribute("nameOfTargetList");
                %>
            </div>
        </div>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>
