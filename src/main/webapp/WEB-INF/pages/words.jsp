<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Revision</title>
    <style>
        <%@include file="/css/style.css"%>
    </style>
    <meta http-equiv='content-type' content='text/html; charset=utf-8' >
</head>
<body>
<div id="header">
    <h1 class="brand">Revision</h1>
    <p class="logout"><a class="logout-link" href="/logout">Logout</a></p>
    <div class="clear-left"></div>
</div>
<div id="container">
    <div id="sidebar">
        <div id="sidebar-top-words"></div>
        <div>
            <p><input class="button-sidebar" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="button-sidebar" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="button-sidebar" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="button-sidebar" type="submit" value="SECTIONS" onclick="location.href='/sections?dictionary_id=${param.get('dictionary_id')}'"></p>
            <p><input class="button-sidebar" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>
    <div id="content">
        <h2>WORDS</h2>
        <input class="add-new-button" type="submit" value="ADD 1 NEW +" onclick="location.href='/words?dictionary_id=${param.get('dictionary_id')}&section_id=${param.get('section_id')}&active_form_add_word=true'">
        <input class="add-list-button" type="submit" value="ADD LIST" onclick="location.href='/words?dictionary_id=${param.get('dictionary_id')}&section_id=${param.get('section_id')}&active_form_import_words=true'">
        <c:if test="${param.get('active_form_add_word') != null}">
            <form action="/words" class="add" method="post">
                <input class="rename-input" type="text" name="word_add_name" value="" placeholder="Enter new word"><br>
                <input class="rename-input" type="text" name="translation_add_name" value="" placeholder="Enter new translation"><br>
                <input class="add-button" type="submit" value="ADD WORD"><br>
                <input type="hidden" name="action" value="add_word">
                <input type="hidden" name="dictionary_id" value="${param.get('dictionary_id')}">
                <input type="hidden" name="section_id" value="${param.get('section_id')}">
                <a class="cancel-link" href="/words?dictionary_id=${param.get('dictionary_id')}&section_id=${param.get('section_id')}">Cancel</a>
            </form>
        </c:if>
        <c:if test="${param.get('active_form_rename_word') != null}">
            <form action="/words" class="add" method="post">
                <input class="rename-input" type="text" name="word_new" value="" placeholder="Enter word"><br>
                <input class="rename-input" name="translation_new" value="" placeholder="Enter translation"><br>
                <input class="add-button" type="submit" value="RENAME WORD"><br>
                <input type="hidden" name="action" value="rename_word">
                <input type="hidden" name="word_id" value="${param.get('word_id')}">
                <input type="hidden" name="word_old" value="${param.get('word_old')}">
                <input type="hidden" name="translation_old" value="${param.get('translation_old')}">
                <input type="hidden" name="dictionary_id" value="${param.get('dictionary_id')}">
                <input type="hidden" name="section_id" value="${param.get('section_id')}">
                <a class="cancel-link" href="/words?dictionary_id=${param.get('dictionary_id')}&section_id=${param.get('section_id')}">Cancel</a>
            </form>
        </c:if>
        <c:if test="${param.get('active_form_import_words') != null}">
            <form class="import add" action="/words" method="post">
                <textarea name="words_line" class="import-input" placeholder=" Enter words here as in example:&#10; word - translation;&#10; word - translation;&#10; word - translation;"></textarea>
                <input type="hidden" name="action" value="import_words">
                <input type="hidden" name="dictionary_id" value="${param.get('dictionary_id')}">
                <input type="hidden" name="section_id" value="${param.get('section_id')}">
                <p><button class="import-words-button" type="submit"> IMPORT </button>
                    <a class="cancel-link-words" href="/words?dictionary_id=${param.get('dictionary_id')}&section_id=${param.get('section_id')}">Cancel</a></p>
            </form>
        </c:if>
        <c:if test="${requestScope.wordList.size() != 0}">
            <form method="post" class="align" onclick='return confirm("Delete all words in this section?")'>
                <input type="hidden" name="action" value="delete_all_words">
                <input type="hidden" name="dictionary_id" value="${param.get('dictionary_id')}">
                <input type="hidden" name="section_id" value="${param.get('section_id')}">
                <input class="delete-all-words-button" type="submit" value="CLEAR ALL">
            </form>
            <div id="table-container">
                <table id="words-table">
                    <tr><th>word</th><th>translation</th></tr>
                    <c:forEach items="${requestScope.wordList}" var="word">
                        <jsp:useBean id="word" type="com.revision.entity.Word"/>
                                <tr>
                                    <td>${word.word}</td>
                                    <td>${word.translation}</td>
                                    <td>
                                        <a class="rename-link" href="/words?active_form_rename_word=true&word_id=${word.id}&section_id=${word.sectionId}&dictionary_id=${word.dictionaryId}&word_old=${word.word}&translation_old=${word.translation}">Change</a>
                                        <form method="post" class="align" onclick='return confirm("Delete word?")'>
                                            <input type="hidden" name="action" value="delete_word">
                                            <input type="hidden" name="word_id" value="${word.id}">
                                            <input type="hidden" name="section_id" value="${word.sectionId}">
                                            <input type="hidden" name="dictionary_id" value="${word.dictionaryId}">
                                            <input class="delete-button" type="submit" value="Delete">
                                        </form>
                                    </td>
                                </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:if test="${requestScope.wordList.size() == 0}">
            <p class="info">You do not have any words. <br> Click on the "ADD 1 NEW +" or "ADD LIST" button to create</p>
        </c:if>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>
