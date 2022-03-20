<%@ page import="com.revision.entity.Section" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8"%>
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
            <p><input class="button-sidebar" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="button-sidebar" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="button-sidebar" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="button-sidebar" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>
    <%
        int dictionary_id = 0;
        try {
            dictionary_id = Integer.valueOf(request.getParameter("dictionary_id"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    %>
    <div id="content">
        <h2>SECTIONS</h2>
        <input class="add-new-button" type="submit" value="ADD NEW +" onclick="location.href='/sections?dictionary_id=<%=dictionary_id%>&active_form_add_section=true'">
        <% if(request.getParameter("active_form_add_section") != null) { %>
        <form action="/sections" class="add" method="post">
            <input style="font-size: 15px;" type="text" name="section_name" value="" placeholder="Enter section name"><br>
            <input class="add-button" type="submit" value="ADD SECTION"><br>
            <input type="hidden" name="dictionary_id" value="<%=dictionary_id%>"><br>
            <input type="hidden" name="action" value="add_section">
            <a class="cancel-link" href="/sections?dictionary_id=<%=dictionary_id%>">Cancel</a>
        </form>
        <% } %>
        <% if(request.getParameter("active_form_rename_section") != null) {
            String dictionaryId = request.getParameter("dictionary_id");
            String sectionId = request.getParameter("section_id"); %>
        <form action="/sections" class="add" method="post">
            <input style="font-size: 15px;" type="text" name="section_new_name" value="" placeholder="Enter section name"><br>
            <input class="add-button" type="submit" value="RENAME SECTION"><br>
            <input type="hidden" name="action" value=rename_section>
            <input type="hidden" name="dictionary_id" value="<%=dictionaryId%>">
            <input type="hidden" name="section_id" value="<%=sectionId%>"><br>
            <a class="cancel-link" href="/sections?dictionary_id=<%=dictionaryId%>">Cancel</a>
        </form>
        <% } %>
        <%
           List<Section> sectionList = (List<Section>) request.getAttribute("sectionList");
        %>
        <%
            if(!sectionList.isEmpty()) {
                for (Section section : sectionList) {
        %>
        <p><div class="align"><input class="submit6" type="submit" value="<%=section.getName()%>" onclick="location.href='/words?dictionary_id=<%=dictionary_id%>&section_id=<%=section.getId()%>'"></div>
        <div class="words-count"><%=section.getWordsCount()%> words</div>
        <div class="align"><input class="submit10" type="submit" value="REPEAT" onclick="location.href='/repeating?start-repeating=start&repeat_by=section&section_id=<%=section.getId()%>&path_return=sections?dictionary_id=<%=dictionary_id%>'"></div>
        <form method="post" style="display:inline-block;" onclick='return confirm("Delete section?")'>
            <input type="hidden" name="action" value="delete_section">
            <input type="hidden" name="section_id" value="<%=section.getId()%>">
            <input type="hidden" name="dictionary_id" value="<%=section.getDictionaryId()%>">
            <input class="delete-button" type="submit" value="Delete">
        </form>
        <form method="post" style="display:inline-block;" onclick='return confirm("Clear section?")'>
            <input type="hidden" name="action" value="clear_section">
            <input type="hidden" name="section_id" value="<%=section.getId()%>">
            <input type="hidden" name="dictionary_id" value="<%=section.getDictionaryId()%>">
            <input class="clear-button" type="submit" value="Clear">
        </form>
        <a class="rename-link" href="/sections?active_form_rename_section=true&section_id=<%=section.getId()%>&dictionary_id=<%=section.getDictionaryId()%>">Rename</a>
        </p>
        <%      }
            }
            else  {
        %>
        <p class="info">You do not have any sections. <br> Click on the "ADD NEW +" button to create</p>
        <%}%>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>
