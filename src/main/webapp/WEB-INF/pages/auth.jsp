<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 24.04.2021
  Time: 4:43
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

    <%  String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage == null) {
            errorMessage = "";
        }
    %>

</head>
<body>
<div id="header"><h1>Revision</h1></div>
<div id="contentauth">
    <div id="containerauth">
        <p class="login"> Log in</p>
        <form name="username" action="/login" method="POST">
            <div class="log-input">
                <input class="input-auth" type="text" name="username" value="" placeholder="Enter login"/>
            </div>
            <div class="log-input">
                <input class="input-auth" type="password" name="password" value="" placeholder="Enter password"/>
                <p style="color: crimson; margin-top: 0px;"><%=errorMessage%></p>
            </div>
            <input class="log-submit" type="submit" value="LOGIN"/><br />
            <a id="reg" href="/registration">Registration</a>
        </form>
    </div>

    <%
        session.removeAttribute("errorMessage");
    %>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>

