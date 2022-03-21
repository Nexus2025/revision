<%@ page contentType="text/html;charset=UTF-8"%>
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
    <div style="clear: left"></div>
</div>
<div id="container">
    <div id="sidebar">
        <div id="sidebar-top"></div>
        <div>
            <p><input class="button-sidebar" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="button-sidebar" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="button-sidebar" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="button-sidebar" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>
    <div id="content">
        <h2>SECTIONS</h2>
        <input class="add-new-button" type="submit" value="ADD NEW +" onclick="location.href='/sections?dictionary_id=${param.get('dictionary_id')}&active_form_add_section=true'">
        <c:if test="${param.get('active_form_add_section') != null}">
            <form action="/sections" class="add" method="post">
                <input class="rename-input" type="text" name="section_name" value="" placeholder="Enter section name"><br>
                <input class="add-button" type="submit" value="ADD SECTION"><br>
                <input type="hidden" name="dictionary_id" value="${param.get('dictionary_id')}"><br>
                <input type="hidden" name="action" value="add_section">
                <a class="cancel-link" href="/sections?dictionary_id=${param.get('dictionary_id')}">Cancel</a>
            </form>
        </c:if>
        <c:if test="${param.get('active_form_rename_section') != null}">
            <form action="/sections" class="add" method="post">
                <input class="rename-input" type="text" name="section_new_name" value="" placeholder="Enter section name"><br>
                <input class="add-button" type="submit" value="RENAME SECTION"><br>
                <input type="hidden" name="action" value=rename_section>
                <input type="hidden" name="dictionary_id" value="${param.get('dictionary_id')}">
                <input type="hidden" name="section_id" value="${param.get('section_id')}"><br>
                <a class="cancel-link" href="/sections?dictionary_id=${param.get('dictionary_id')}">Cancel</a>
            </form>
        </c:if>
        <c:forEach items="${requestScope.sectionList}" var="section">
            <jsp:useBean id="section" type="com.revision.entity.Section"/>
            <p><div class="align"><input class="section-button" type="submit" value="${section.name}" onclick="location.href='/words?dictionary_id=${section.dictionaryId}&section_id=${section.id}'"></div>
            <div class="words-count">${section.wordsCount} words</div>
            <div class="align"><input class="repeat-button" type="submit" value="REPEAT" onclick="location.href='/repeating?start-repeating=start&repeat_by=section&section_id=${section.id}&path_return=sections?dictionary_id=${section.dictionaryId}'"></div>
            <form method="post" class="align" onclick='return confirm("Delete section?")'>
                <input type="hidden" name="action" value="delete_section">
                <input type="hidden" name="section_id" value="${section.id}">
                <input type="hidden" name="dictionary_id" value="${section.dictionaryId}">
                <input class="delete-button" type="submit" value="Delete">
            </form>
            <form method="post" class="align" onclick='return confirm("Clear section?")'>
                <input type="hidden" name="action" value="clear_section">
                <input type="hidden" name="section_id" value="${section.id}">
                <input type="hidden" name="dictionary_id" value="${section.dictionaryId}">
                <input class="clear-button" type="submit" value="Clear">
            </form>
            <a class="rename-link" href="/sections?active_form_rename_section=true&section_id=${section.id}&dictionary_id=${section.dictionaryId}">Rename</a>
            </p>
        </c:forEach>
        <c:if test="${requestScope.sectionList.size() == 0}">
            <p class="info">You do not have any sections. <br> Click on the "ADD NEW +" button to create</p>
        </c:if>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>