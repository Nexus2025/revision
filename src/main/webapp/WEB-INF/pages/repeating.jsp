<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Revision</title>
    <style>
        <%@include file="/css/style.css"%>
    </style>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.0/jquery.min.js">
    </script>
    <script>
        <%@include file="/js/script.js"%>
    </script>
</head>
<body onload="start()">
<div id="header">
    <h1 class="brand">Revision</h1>
    <p class="logout"><a class="logout-link" href="/logout">Logout</a></p>
    <div class="clear-left"></div>
</div>
<div id="container">
    <div id="sidebar">
        <div id="sidebar-top"></div>
        <div>
            <p><input class="button-sidebar" type="submit" value="DASHBOARD" onclick="location.href='/main'"></p>
            <p><input class="button-sidebar" type="submit" value="IMPORT" onclick="location.href='/import'"></p>
            <p><input class="button-sidebar" type="submit" value="DICTIONARIES" onclick="location.href='/dictionaries'"></p>
        </div>
    </div>
    <div id="content">
        <div>
            <h2>REPEATING</h2>
            <p class="repeating-subtitle">
            <b class="list-name">${sessionScope.get('nameOfTargetList')}</b>
            <b id="countOfGuessedWords">0</b>
            <b>/&nbsp;${sessionScope.get('countOfAllWords')}</b>
            </p>
                <div class="repeating-word">
                    <div class="word-table-header">WORD</div>
                    <p><span id="word"></span></p>
                </div>
                <div class="repeating-translation">
                    <div class="translation-table-header">TRANSLATION</div>
                    <p><span id="translation"></span></p>
                </div>
        </div>
        <div class="buttons">
            <button id="btnShow">SHOW</button>
            <button id="btnRight">RIGHT</button>
            <button id="btnWrong">WRONG</button>
            <a id="rename" class="repeating-back-button" href="${sessionScope.get('pathReturn')}">BACK</a>
        </div>
    </div>
</div>
<div id="footer"> Developed by Roman F</div>
</body>
</html>
