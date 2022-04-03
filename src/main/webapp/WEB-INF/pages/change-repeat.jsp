<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <p><input class="button-sidebar" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="button-sidebar" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
        </div>
    </div>
    <div id="content">
        <h2>CHANGE</h2>
        <c:if test="${requestScope.get('countOfAllWords') != 0}">
            <div class="change-dictionary">
                <p>Repeat by Dictionary</p>
                <form action="/repeating">
                    <div class="select">
                        <select class="dictionaries-select" name="dictionary_id">
                            <option selected disabled>Select dictionary to repeat</option>
                            <c:forEach var="dictionary" items="${requestScope.get('dictionaryList')}">
                                <jsp:useBean id="dictionary" type="com.revision.entity.Dictionary"/>
                                <option value="${dictionary.id}">${dictionary.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="hidden" name="start-repeating" value="start">
                    <input type="hidden" name="repeat_by" value="dictionary">
                    <input type="hidden" name="path_return" value="change">
                    <p><input class="submit-reverse" type="submit" value="START"></p>
                </form>
            </div>
            <div class="change-section">
                <p>Repeat by Section</p>
                <form action="/repeating">
                    <div class="select">
                        <select class="dictionaries-select" name="section_id">
                            <option selected disabled>Select section to repeat</option>
                            <c:forEach var="section" items="${requestScope.get('sectionList')}">
                                <jsp:useBean id="section" type="com.revision.entity.Section"/>
                                <option value="${section.id}">${section.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="hidden" name="start-repeating" value="start">
                    <input type="hidden" name="repeat_by" value="section">
                    <input type="hidden" name="path_return" value="change">
                    <p><input class="submit-reverse" type="submit" value="START"></p>
                </form>
            </div>
        </c:if>
        <c:if test="${requestScope.get('countOfAllWords') == 0}">
            <p class="info">You do not have any words. <br> At first add words to your dictionary</p>
            <p><a class="info" style="color: #3e4346;" href="/dictionaries">GO TO DICTIONARIES SECTION</a></p>
        </c:if>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>