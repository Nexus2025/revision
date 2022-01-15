<%@ page import="com.revision.entities.User" %>
<%@ page import="com.revision.entities.Dictionary" %>
<%@ page import="com.revision.model.DictionaryManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.revision.model.SectionManager" %>
<%@ page import="com.revision.entities.Section" %>
<%@ page import="com.revision.model.UserManager" %>
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
            <p><input class="submit" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
        </div>
    </div>

    <%
        User user = (User) session.getAttribute("user");

        DictionaryManager dm = new DictionaryManager();
        List<Dictionary> dictionaryList = dm.getDictionaryListByUserId(user.getUserId());

        SectionManager sm = new SectionManager();
        List<Section> sectionList = sm.getSectionListByUserId(user.getUserId());

        UserManager um = new UserManager();
        int wordsCount = um.getCountOfWordsByUserId(user.getUserId());
    %>

    <div id="content">
        <h2>CHANGE</h2>

        <% if (wordsCount > 0) { %>

        <div class="change_dict">
            <p>Repeat by Dictionary</p>
            <form action="/repeating">
                <div class="select">
                    <select name="dictionary_id">
                        <option selected disabled>Select dictionary to repeat</option>

                        <% for (Dictionary dictionary : dictionaryList) { %>

                        <option value="<%=dictionary.getDictionaryId()%>"><%=dictionary.getName()%></option>

                        <% } %>

                    </select>
                </div>
                <input type="hidden" name="start-repeating" value="start">
                <input type="hidden" name="repeat_by" value="dictionary">
                <input type="hidden" name="path_return" value="change">
                <p><input class="submit9" type="submit" value="START"></p>
            </form>
        </div>

        <div class="change_sect">
            <p>Repeat by Section</p>
            <form action="/repeating">
                <div class="select">
                    <select name="section_id">
                        <option selected disabled>Select section to repeat</option>

                        <% for (Section section : sectionList) { %>

                        <option value="<%=section.getSectionId()%>"><%=section.getName()%></option>

                        <% } %>

                    </select>
                </div>
                <input type="hidden" name="start-repeating" value="start">
                <input type="hidden" name="repeat_by" value="section">
                <input type="hidden" name="path_return" value="change">
                <p><input class="submit9" type="submit" value="START"></p>
            </form>
        </div>

        <% } else { %>

        <p class="info">You do not have any words. <br> At first add words to your dictionary</p>
        <p><a class="info" style="color: #3e4346;" href="/dictionaries">GO TO DICTIONARIES SECTION</a></p>

        <% } %>

    </div>
</div>

<div id="footer"> Developed by Roman F</div>
</body>
</html>
