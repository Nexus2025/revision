<%@ page contentType="text/html;charset=UTF-8" %>
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
    <div style="clear: left"></div>
</div>
<div id="container">
    <div id="sidebar">
        <div id="sidebar-top"></div>
        <div id="sd=bot">
            <p><input class="submit-main" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="submit-main" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="submit-main" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
        </div>
    </div>
    <div id="content">
        <div>
            <h2>RESULTS</h2>
            <%
                int wrongAnswers = (Integer) session.getAttribute("wrongAnswers");
                int rightAnswers = (Integer) session.getAttribute("rightAnswers");
                String name = (String) session.getAttribute("nameOfTargetList");
                String pathReturn = (String) session.getAttribute("pathReturn");
                String repeatBy = (String) session.getAttribute("repeatBy");
                int id = (Integer) session.getAttribute("id");
                String path = repeatBy.equals("dictionary") ? "dictionary_id=" + id : "section_id=" + id;
            %>
            <div class="indent">
                <p class="dashboard-data-1" style="color: #f44e28"><%=name%>
                </p>
                <p class="dashboard-data-1">RIGHT ANSWERS <%=rightAnswers%>
                </p>
                <p class="dashboard-data-1">WRONG ANSWERS <%=wrongAnswers%>
                </p>
                <p><input class="submit4" type="submit" value="REPEAT AGAIN"
                          onclick="location.href='/repeating?start-repeating=start&repeat_by=<%=repeatBy%>&<%=path%>'">
                    <input class="submit4 sub4" type="submit" value="BACK" onclick="location.href='<%=pathReturn%>'">
                </p>
                <%
                    //CLEAR ATTRIBUTES AFTER DOWNLOAD PAGE
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
