<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>firstWordId: <%=session.getAttribute("firstWordId")%></p>
<p>wordMap: <%=session.getAttribute("wordMap")%></p>
<p>maxId: <%=session.getAttribute("maxId")%></p>
<p>rightAnswers: <%=session.getAttribute("rightAnswers")%></p>
<p>wrongAnswers: <%=session.getAttribute("wrongAnswers")%></p>
<p>repeatBy: <%=session.getAttribute("repeatBy")%></p>
<p>id: <%=session.getAttribute("id")%></p>
<p>pathReturn: <%=session.getAttribute("pathReturn")%></p>
<p>reverse: <%=session.getAttribute("reverse")%></p>
<p>domain: <%=request.getServerName()%></p>
<p>port: <%=request.getServerPort()%></p>
<p>errorMessage: <%=session.getAttribute("errorMessage")%></p>
<p>countOfAllWords: <%=session.getAttribute("countOfAllWords")%></p>
<p>countOfGuessedWords: <%=session.getAttribute("countOfGuessedWords")%></p>
<p>nameOfTargetList: <%=session.getAttribute("nameOfTargetList")%></p>
</body>
</html>
