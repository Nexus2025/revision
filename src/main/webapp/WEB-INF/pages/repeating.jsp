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

    <%
        Object countOfAllWords = session.getAttribute("countOfAllWords");
        Object pathReturn = session.getAttribute("pathReturn");

        Object name = session.getAttribute("nameOfTargetList");
    %>

</head>
<body onload="start()">
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

    <div id="content">
        <div>
            <h2>REPEATING</h2>
            <p style="font-size:1.8vw; color: #868282; margin: 2px 10px;">
            <b style="color:#f44e28; padding-right:10px"><%=name%></b>
            <b id="countOfGuessedWords">0</b>
            <b>/ <%=countOfAllWords%></b>
            </p>

                <div class="word1">
                    <div style="background: rgb(44 168 198); color: white; padding: 5px; font-size: 25px;">WORD</div>
                    <p><span id="word"></span></p>
                </div>


                <div class="word2">
                    <div style="background: rgb(44 168 198); color: white; padding: 5px; font-size: 25px;">TRANSLATION</div>
                    <p><span id="translation"></span></p>
                </div>

        </div>

        <div class="buttons">
            <button id="btnShow">SHOW</button>
            <button id="btnRight">RIGHT</button>
            <button id="btnWrong">WRONG</button>
            <a id="rename" style="margin-left: 20px; color: #656363; font-size: 1.3vw;" href="<%=pathReturn%>">BACK</a>
        </div>

    </div>
</div>

<div id="footer"> Developed by Roman F</div>
</body>
</html>
