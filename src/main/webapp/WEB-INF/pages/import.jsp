<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Revision</title>
    <style>
        @import url("/css/style.css");
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
            <p><input class="button-sidebar" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="button-sidebar" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>
    <div id="content">
        <div id="content-header">
            <h2>IMPORT</h2>
            <c:if test="${requestScope.dictionaries.size() != 0}">
            <div class="import_csv">
                <h3>CSV</h3>
                <p><a id="rename" class="color-link-csv" href="/download-csv">Download the sample csv</a></p>
                <form enctype="multipart/form-data" method="post">
                    <div class="select">
                        <select class="dictionaries-select" name="dictionary_id">
                            <option selected disabled>Select the dictionary to import</option>
                            <c:forEach var="dictionary" items="${requestScope.dictionaries}">
                                <jsp:useBean id="dictionary" type="com.revision.entity.Dictionary"></jsp:useBean>
                                <option value="${dictionary.id}">${dictionary.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <p><input type="file" name="csv_file" accept=".csv, text/csv"></p>
                    <p><input class="submit-reverse" type="submit" value="UPLOAD"></p>
                </form>
            </div>
            <div class="import_req">
                <p class="color-requirements">File requirements:</p>
                <p>Formatting <a class="color-requirements-description">UTF-8</a><br><br>
                    Delimiter <a class="color-requirements-description">";"</a><br><br>
                    3 columns <br> <a class="color-requirements-description">"section", "word", "translation"</a></p>
            </div>
            <div class="import_sheets">
                <h3>GOOGLE SHEETS</h3>
                <p><input class="sheets-button" type="submit" value="${sessionScope.credential != null ? "GO TO IMPORT" : "SIGN IN Google Account"}" onclick="location.href='${sessionScope.credential != null ? "/import-sheets" : "/oauth2ask"}'"></p>
            </div>
            </c:if>
            <c:if test="${requestScope.dictionaries.size() == 0}">
                <p class="info">You do not have any dictionaries. <br> Go to dictionaries section and create it</p>
                <p><a class="info" style="color: #3e4346;" href="/dictionaries">GO TO DICTIONARIES SECTION</a></p>
            </c:if>
        </div>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>