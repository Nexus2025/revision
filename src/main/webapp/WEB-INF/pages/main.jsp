<%@ page import="com.revision.entities.User" %>
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

    <%
        User user = (User) session.getAttribute("user");
        String userName = user.getLogin();

        UserManager um = new UserManager();
        int countOfWords = um.getCountOfWordsByUserId(user.getUserId());
        int countOfDictionaries = um.getCountOfDictionariesByUserId(user.getUserId());

        String reverse = (String) session.getAttribute("reverse");
    %>

    <div id="sidebar">
        <div id="sd-top">
            <% if (user.getRole() == 2) { %>
            <p><a style="background-color: #656363; text-decoration:none" class="submit" href="/tech" target="_blank">TECH INFORMATION</a></p>
            <%}%>

        </div>
        <div id="sd=bot">
            <p><input class="submit" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="submit" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="submit" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>

    <div id="content">
        <div id="data">
        <h2>DASHBOARD</h2>

            <div class="main">
                <div style="float: left">
                    <p class="p1">User: <%=userName%></p>
                    <p class="p2"><%=countOfDictionaries%> DICTIONARIES / <%=countOfWords%> WORDS</p>
                </div>


                <div style="float: left; padding-left: 50px">
                    <p class="p1">REVERSE: <%=reverse%></p>

                    <% if (reverse.equals("ON")) { %>
                    <p><input class="submit9" type="submit" value="DISABLE" onclick="location.href='/main?set_reverse=off'"></p>

                    <% } else { %>
                    <p><input class="submit9" type="submit" value="ENABLE" onclick="location.href='/main?set_reverse=on'"></p>
                    <% } %>

                    <p style="font-size: 15px; font-family: monospace ">Needed for repeating.<br>Swaps word and translation</p>
                </div>

            </div>

        </div>
    </div>
</div>

<div id="footer"> Developed by Roman F</div>
</body>
</html>
