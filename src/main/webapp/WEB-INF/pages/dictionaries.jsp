<%@ page import="com.revision.entity.Dictionary" %>
<%@ page import="com.revision.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.revision.service.DictionaryService" %>
<%--
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
            <p><input class="submit" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="submit" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>

    <div id="content">
        <h2>DICTIONARIES</h2>

        <%----------------------------------------------------------%>

        <input class="submit3" type="submit" value="ADD NEW +" onclick="location.href='/dictionaries?active_form_add=true'">

        <%
            User user = (User) session.getAttribute("user");

            DictionaryService dm = new DictionaryService();
            List<Dictionary> dictionaryList = dm.getAll(user.getId());
        %>


        <% if(request.getParameter("active_form_add") != null) { %>
        <form action="/dictionaries" class="add" method="post">
            <input style="font-size: 15px;" type="text" name="dictionary_name" value="" placeholder="Enter dictionary name"/><br>
            <input class="submit4" type="submit" value="ADD DICTIONARY"><br><br>
            <input type="hidden" name="action" value="add_dictionary">
            <a id="reg" href="/dictionaries">Cancel</a>
        </form>
        <% } %>

        <% if(request.getParameter("active_form_rename") != null) {
            String dictionaryId = request.getParameter("dictionary_id"); %>
        <form action="/dictionaries" class="add" method="post">
            <input style="font-size: 15px;" type="text" name="dictionary_new_name" value="" placeholder="Enter dictionary name"><br>
            <input class="submit4" type="submit" value="RENAME DICTIONARY"><br>
            <input type="hidden" name="action" value="rename_dictionary">
            <input type="hidden" name="dictionary_id" value="<%=dictionaryId%>">
            <a id="reg" href="/dictionaries">Cancel</a>
        </form>
        <% } %>

        <%----------------------------------------------------------%>

        <% if(!dictionaryList.isEmpty()) {
               for (Dictionary dictionary : dictionaryList) { %>

        <p><div class="sub"><input class="submit5" type="submit" value="<%=dictionary.getName()%>" onclick="location.href='/sections?dictionary_id=<%=dictionary.getId()%>'"></div>
        <div class="words_count"><%=dictionary.getWordsCount()%> words</div>
        <div class="sub"><input class="submit11" type="submit" value="REPEAT" onclick="location.href='/repeating?start-repeating=start&repeat_by=dictionary&dictionary_id=<%=dictionary.getId()%>&path_return=dictionaries'"></div>
        <form method="post" style="display:inline-block;" onclick='return confirm("Delete dictionary?")'>
            <input type="hidden" name="action" value="delete_dictionary">
            <input type="hidden" name="dictionary_id" value="<%=dictionary.getId()%>">
            <input class="delete" type="submit" value="Delete">
        </form>
        <form method="post" style="display:inline-block;" onclick='return confirm("Clear dictionary?")'>
            <input type="hidden" name="action" value="clear_dictionary">
            <input type="hidden" name="dictionary_id" value="<%=dictionary.getId()%>">
            <input class="clear2" type="submit" value="Clear">
        </form>
        <a class="rename" href="/dictionaries?active_form_rename=true&dictionary_id=<%=dictionary.getId()%>">Rename</a>
        </p>
        <%  }
        }
            if (dictionaryList.isEmpty()) {
        %>
        <p class="info">You do not have any dictionaries. <br> Click on the "ADD NEW +" button to create</p>
        <% } %>


    </div>
</div>

<div id="footer"> Developed by Roman F</div>
</body>
</html>
