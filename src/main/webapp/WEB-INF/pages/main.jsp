<%@ page import="com.revision.entity.Role" %>
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
    <jsp:useBean id="user" type="com.revision.entity.User" scope="session"></jsp:useBean>
    <div id="sidebar">
        <div id="sidebar-top">
            ${user.role==Role.ADMIN ? '<p><a class="submit-main-admin" href="/tech" target="_blank">TECH INFORMATION</a></p>' : ''}
        </div>
        <div>
            <p><input class="submit-main" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="submit-main" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
            <p><input class="submit-main" type="submit" value="REPEAT" onclick="location.href='/change'"></p>
        </div>
    </div>
    <div id="content">
        <div id="content-header">
        <h2>DASHBOARD</h2>
            <div class="indent">
                <div class="content-left-column">
                    <p class="dashboard-data-1">User: ${user.userName}</p>
                    <p class="dashboard-data-2">${requestScope.get("dictionariesCount")} DICTIONARIES / ${requestScope.get("wordsCount")} WORDS</p>
                </div>
                <div class="content-right-column">
                    <p class="dashboard-data-1">REVERSE: ${sessionScope.get("reverse")}</p>
                    <c:set var="buttonText" scope="page" value="${sessionScope.get('reverse') == 'ON' ? 'DISABLE' : 'ENABLE'}"></c:set>
                    <c:set var="buttonLink" scope="page" value="${sessionScope.get('reverse') == 'ON' ? '/main?set_reverse=off' : '/main?set_reverse=on'}"></c:set>
                    <p><input class="submit-reverse" type="submit" value="${buttonText}" onclick="location.href='${buttonLink}'"></p>
                    <p class="reverse-description">Needed for repeating.<br>Swaps word and translation</p>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>
