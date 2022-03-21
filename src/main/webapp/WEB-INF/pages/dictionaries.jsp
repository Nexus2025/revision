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
            <p><input class="button-sidebar" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>
    <div id="content">
        <h2>DICTIONARIES</h2>
        <input class="add-new-button" type="submit" value="ADD NEW +" onclick="location.href='/dictionaries?active_form_add=true'">
        <c:if test="${param.get('active_form_add') != null}">
            <form action="/dictionaries" class="add" method="post">
                <input class="rename-input" type="text" name="dictionary_name" value="" placeholder="Enter dictionary name"/><br>
                <input class="add-button" type="submit" value="ADD DICTIONARY"><br><br>
                <input type="hidden" name="action" value="add_dictionary">
                <a class="cancel-link" href="/dictionaries">Cancel</a>
            </form>
        </c:if>
        <c:if test="${param.get('active_form_rename') != null}">
            <form action="/dictionaries" class="add" method="post">
                <input class="rename-input" type="text" name="dictionary_new_name" value="" placeholder="Enter dictionary name"><br>
                <input class="add-button" type="submit" value="RENAME DICTIONARY"><br>
                <input type="hidden" name="action" value="rename_dictionary">
                <input type="hidden" name="dictionary_id" value="${param.get('dictionary_id')}">
                <a class="cancel-link" href="/dictionaries">Cancel</a>
            </form>
        </c:if>
        <c:forEach items="${requestScope.dictionaryList}" var="dictionary">
            <jsp:useBean id="dictionary" type="com.revision.entity.Dictionary"/>
            <p><div class="align"><input class="dictionary-button" type="submit" value="${dictionary.name}" onclick="location.href='/sections?dictionary_id=${dictionary.id}'"></div>
            <div class="words-count">${dictionary.wordsCount} words</div>
            <div class="align"><input class="repeat-button" type="submit" value="REPEAT" onclick="location.href='/repeating?start-repeating=start&repeat_by=dictionary&dictionary_id=${dictionary.id}&path_return=dictionaries'"></div>
            <form method="post" class="align" onclick='return confirm("Delete dictionary?")'>
                <input type="hidden" name="action" value="delete_dictionary">
                <input type="hidden" name="dictionary_id" value="${dictionary.id}">
                <input class="delete-button" type="submit" value="Delete">
            </form>
            <form method="post" class="align" onclick='return confirm("Clear dictionary?")'>
                <input type="hidden" name="action" value="clear_dictionary">
                <input type="hidden" name="dictionary_id" value="${dictionary.id}">
                <input class="clear-button" type="submit" value="Clear">
            </form>
            <a class="rename-link" href="/dictionaries?active_form_rename=true&dictionary_id=${dictionary.id}">Rename</a>
            </p>
        </c:forEach>
        <c:if test="${requestScope.dictionaryList.size() == 0}">
            <p class="info">You do not have any dictionaries. <br> Click on the "ADD NEW +" button to create</p>
        </c:if>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>
