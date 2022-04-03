<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Revision</title>
    <style>
        @import url("/css/style.css");
    </style>
</head>
<body>
<div id="header"><h1>Revision</h1></div>
<div id="content-auth">
    <div id="content-auth-inner">
        <p class="login"> Log in</p>

        <form name="username" action="/login" method="POST">
            <div class="log-input">
                <input class="input-auth" type="text" name="username" value="" placeholder="Enter login"/>
            </div>
            <div class="log-input">
                <input class="input-auth" type="password" name="password" value="" placeholder="Enter password"/>
                <p class="auth-error-message">${sessionScope.get("errorMessage") != null ? sessionScope.get("errorMessage") : ''}</p>
                <c:remove var="errorMessage" scope="session"/>
            </div>
            <input class="log-submit" type="submit" value="LOG IN"/><br/>
        </form>

        <form name="username" action="/login" method="POST">
            <input type="hidden" name="login_demo" value="true">
            <input class="log-demo" type="submit" value="LOG IN AS DEMO USER"/><br/>
        </form>

        <a id="reg" href="/registration">Registration</a>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>