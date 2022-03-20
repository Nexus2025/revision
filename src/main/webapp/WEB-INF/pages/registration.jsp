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
<div id="header"><h1>Revision</h1></div>
<div id="content-auth">
    <div id="content-auth-inner">
        <p class="login">Create an account</p>
        <form name="username" action="/registration" method="POST">
            <div class="log-input">
                <input class="input-auth" type="text" name="username" value="" placeholder="Enter login"/>
            </div>
            <div class="log-input">
                <input class="input-auth" type="password" name="password" value="" placeholder="Enter password"/>
            </div>
            <div class="log-input">
                <input class="input-auth" type="password" name="confirm_password" value="" placeholder="Confirm password"/>
                <p class="auth-error-message">${sessionScope.get("errorMessage") != null ? sessionScope.get("errorMessage") : ''}</p>
                <c:remove var="errorMessage" scope="session"/>
            </div>
            <input class="log-submit" type="submit" value="CREATE AN ACCOUNT"/><br />
            <a id="reg" href="/login">Back to Login</a>
        </form>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>

