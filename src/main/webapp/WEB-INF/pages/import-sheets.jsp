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
            <h2>IMPORT FROM GOOGLE SHEETS</h2>
            <c:if test="${requestScope.dictionaries.size() != 0}">
                <div class="import_csv">
                    <form method="post">
                        <div class="select">
                            <p>Dictionaries:</p>
                            <select class="dictionaries-select" name="dictionary_id">
                                <option selected disabled>Select the dictionary to import</option>
                                <c:forEach var="dictionary" items="${requestScope.dictionaries}">
                                    <jsp:useBean id="dictionary" type="com.revision.entity.Dictionary"></jsp:useBean>
                                    <option value="${dictionary.id}">${dictionary.name}</option>
                                </c:forEach>
                            </select>
                            <br>
                            <p>Google sheets:</p>
                            <select class="sheets-select" name="sheet_id">
                                <option selected disabled>Select the sheet where import from</option>
                                <c:forEach var="sheet" items="${requestScope.sheets}">
                                    <jsp:useBean id="sheet" type="com.revision.google.entity.SheetTO"></jsp:useBean>
                                    <option value="${sheet.id}">${sheet.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <p><input class="submit-reverse" type="submit" value="IMPORT"></p>
                    </form>
                </div>
                <div class="import_req">
                    <p class="color-requirements">Requirements:</p>
                    <p>3 columns <br> <a class="color-requirements-description">"section", "word", "translation"</a></p>
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