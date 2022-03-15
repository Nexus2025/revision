<%@ page import="com.revision.entity.User" %>
<%@ page import="com.revision.service.DictionaryService" %>
<%@ page import="com.revision.entity.Dictionary" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <p class="logout"><a class="logout2" href="/logout">Logout</a></p>
    <div style="clear: left"></div>
</div>
<div id="container">

    <div id="sidebar">
        <div id="sd-top"></div>
        <div id="sd=bot">
            <p><input class="submit" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="submit" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="submit" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>

    <div id="content">
        <div id="data">
            <h2>IMPORT</h2>

            <%
                User user = (User) session.getAttribute("user");
                int userId = user.getId();

                DictionaryService dm = new DictionaryService();
                List<Dictionary> dictionaryList = dm.getAll(userId);
            %>

            <%
                if (!dictionaryList.isEmpty()) {
            %>

            <div class="import_csv">
                <p><a id="rename" style="color: rgb(230, 120, 42);" href="/download-csv">Download the sample csv</a></p>
                <form enctype="multipart/form-data" method="post">
                    <div class="select">
                        <select name="dictionary_id">
                            <option selected disabled>Select the dictionary to import</option>

                            <% for (Dictionary dictionary : dictionaryList) { %>

                            <option value="<%=dictionary.getId()%>"><%=dictionary.getName()%></option>

                            <% } %>

                        </select>
                    </div>
                    <p><input type="file" name="csv_file" accept=".csv, text/csv"></p>
                    <p><input class="submit9" type="submit" value="UPLOAD"></p>
                </form>
            </div>
            <div class="import_req">
                <p style="color: rgb(214, 29, 29);">File requirements:</p>
                <p>Formatting <a style="color: rgb(94, 94, 94);">UTF-8</a><br><br>
                    Delimiter <a style="color: rgb(94, 94, 94);">";"</a><br><br>
                    3 columns <br> <a style="color: rgb(94, 94, 94);">"section", "word", "translation"</a></p>
            </div>

            <% } %>

            <%
                if (dictionaryList.isEmpty()) {
            %>

            <p class="info">You do not have any dictionaries. <br> Go to dictionaries section and create it</p>
            <p><a class="info" style="color: #3e4346;" href="/dictionaries">GO TO DICTIONARIES SECTION</a></p>

            <% } %>

        </div>
    </div>
</div>

<div id="footer"> Developed by Roman F</div>
</body>
</html>
