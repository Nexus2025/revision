<%@ page import="java.util.List" %>
<%@ page import="com.revision.model.WordManager" %>
<%@ page import="com.revision.entities.Word" %>
<%@ page import="com.revision.entities.User" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 18.04.2021
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <p class="logout"><a class="logout2" href="/logout">Logout</a></p>
    <div style="clear: left"></div>
</div>
<div id="container">

    <%
        int sectionIdNumber = 0;
        int dictionaryIdNumber = 0;

        try {
            sectionIdNumber = Integer.valueOf(request.getParameter("section_id"));
            dictionaryIdNumber = Integer.valueOf(request.getParameter("dictionary_id"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    %>

    <div id="sidebar">
        <div id="sd-top-words"></div>
        <div id="sd=bot">
            <p><input class="submit" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="submit" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="submit" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="submit" type="submit" value="SECTIONS" onclick="location.href='/sections?dictionary_id=<%=dictionaryIdNumber%>'"></p>
            <p><input class="submit" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>


    <div id="content">
        <h2>WORDS</h2>

    <%----------------------------------------------------------%>


        <input class="submit3" type="submit" value="ADD 1 NEW +" onclick="location.href='/words?dictionary_id=<%=dictionaryIdNumber%>&section_id=<%=sectionIdNumber%>&active_form_add_word=true'">

        <input class="submit8" type="submit" value="ADD LIST" onclick="location.href='/words?dictionary_id=<%=dictionaryIdNumber%>&section_id=<%=sectionIdNumber%>&active_form_import_words=true'">

        <% if(request.getParameter("active_form_add_word") != null) {
            String sectionId = request.getParameter("section_id");
            String dictionaryId = request.getParameter("dictionary_id"); %>

        <form action="/words" class="add" method="post">
            <input style="font-size: 15px;" type="text" name="word_add_name" value="" placeholder="Enter new word"><br>
            <input style="font-size: 15px;" type="text" name="translation_add_name" value="" placeholder="Enter new translation"><br>
            <input class="submit4" type="submit" value="ADD WORD"><br>
            <input type="hidden" name="action" value="add_word">
            <input type="hidden" name="dictionary_id" value="<%=dictionaryId%>">
            <input type="hidden" name="section_id" value="<%=sectionId%>">
            <a id="reg" href="/words?dictionary_id=<%=dictionaryId%>&section_id=<%=sectionId%>">Cancel</a>
        </form>

        <% } %>


        <% if(request.getParameter("active_form_rename_word") != null) {

            String oldWord = request.getParameter("word_old");
            String oldTranslation = request.getParameter("translation_old");
            String wordId = request.getParameter("word_id");
            String sectionId = request.getParameter("section_id");
            String dictionaryId = request.getParameter("dictionary_id"); %>

        <form action="/words" class="add" method="post">
            <input style="font-size: 15px;" type="text" name="word_new" value="" placeholder="Enter word"><br>
            <input style="font-size: 15px;" type="text" name="translation_new" value="" placeholder="Enter translation"><br>
            <input class="submit4" type="submit" value="RENAME WORD"><br>
            <input type="hidden" name="action" value="rename_word">
            <input type="hidden" name="word_id" value="<%=wordId%>">
            <input type="hidden" name="word_old" value="<%=oldWord%>">
            <input type="hidden" name="translation_old" value="<%=oldTranslation%>">
            <input type="hidden" name="dictionary_id" value="<%=dictionaryId%>">
            <input type="hidden" name="section_id" value="<%=sectionId%>">
            <a id="reg" href="/words?dictionary_id=<%=dictionaryId%>&section_id=<%=sectionId%>">Cancel</a>
        </form>

        <% } %>


        <% if(request.getParameter("active_form_import_words") != null) {
            String sectionId = request.getParameter("section_id");
            String dictionaryId = request.getParameter("dictionary_id"); %>

        <form class="import add" action="/words" method="post">
                    <textarea name="words_line" class="import_input" placeholder=" Enter words here as in example:

 word - translation;
 word - translation;
 word - translation;"
                    ></textarea>
            <input type="hidden" name="action" value="import_words">
            <input type="hidden" name="dictionary_id" value="<%=dictionaryId%>">
            <input type="hidden" name="section_id" value="<%=sectionId%>">
            <p>
                <button style="margin: 0px;" class="submit4" type="submit"> IMPORT </button>
                <a id="reg" style="margin-left: 20px;" href="/words?dictionary_id=<%=dictionaryId%>&section_id=<%=sectionId%>">Cancel</a>
            </p>
        </form>

        <% } %>


    <%----------------------------------------------------------%>

        <%  User user = (User) session.getAttribute("user");
            int userId = user.getUserId();

            WordManager wm = new WordManager();
            List<Word> wordList = wm.getWordListBySectionId(sectionIdNumber, userId);

        %>


        <% if(!wordList.isEmpty()) { %>

<%--            <input class="submit7" onclick='return confirm("Delete all words in this section?")' type="submit" value="CLEAR ALL" onclick="location.href='/words?dictionary_id=<%=dictionaryIdNumber%>&section_id=<%=sectionIdNumber%>&action=delete_all_words'">--%>
            <a class="submit7" onclick='return confirm("Delete all words in this section?")' href="/words?dictionary_id=<%=dictionaryIdNumber%>&section_id=<%=sectionIdNumber%>&action=delete_all_words">CLEAR ALL</a>


        <%
                out.write("<div id=\"table-container\">");
                out.write("<table id=\"table1\">");
                out.write("<tr><th>word</th><th>translation</th></tr>");
                for (Word word : wordList) {
        %>
            <tr>
                <td><%=word.getWord()%></td>
                <td><%=word.getTranslation()%></td>
                <td>
                    <a class="rename" href="/words?active_form_rename_word=true&word_id=<%=word.getWordId()%>&section_id=<%=sectionIdNumber%>&dictionary_id=<%=dictionaryIdNumber%>&word_old=<%=word.getWord()%>&translation_old=<%=word.getTranslation()%>">Change</a>
                    <a class="delete" onclick='return confirm("Delete word?")' href="/words?action=delete_word&word_id=<%=word.getWordId()%>&section_id=<%=sectionIdNumber%>&dictionary_id=<%=dictionaryIdNumber%>">Delete</a>
                </td>
            </tr>
        <%      }
            out.write("</table>");
            out.write("</div>");
        }
            if (wordList.isEmpty()) {
        %>
        <p class="info">You do not have any words. <br> Click on the "ADD 1 NEW +" or "ADD LIST" button to create</p>
        <%}%>


    </div>
</div>

<div id="footer"> Developed by Roman F</div>
</body>
</html>
