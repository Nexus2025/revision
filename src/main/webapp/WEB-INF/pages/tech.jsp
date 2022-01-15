<%@ page import="com.revision.entities.User" %>
<%@ page import="com.revision.model.UserManager" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 09.05.2021
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    String userName = user.getLogin();

    UserManager um = new UserManager();
    int countOfWords = um.getCountOfWordsByUserId(user.getUserId());
    int countOfDictionaries = um.getCountOfDictionariesByUserId(user.getUserId());

    Object firstWordId = session.getAttribute("firstWordId");
    Object wordMap = session.getAttribute("wordMap");
    Object maxId = session.getAttribute("maxId");
    Object rightAnswers = session.getAttribute("rightAnswers");
    Object wrongAnswers = session.getAttribute("wrongAnswers");
    Object repeatBy = session.getAttribute("repeatBy");
    Object id = session.getAttribute("id");
    Object pathReturn = session.getAttribute("pathReturn");
    Object reverse = session.getAttribute("reverse");
    Object errorMessage = session.getAttribute("errorMessage");
    Object countOfAllWords = session.getAttribute("countOfAllWords");
    Object countOfGuessedWords = session.getAttribute("countOfGuessedWords");
    Object nameOfTargetList = session.getAttribute("nameOfTargetList");
    Object userLogin = session.getAttribute("userLogin");
    Object userPassword = session.getAttribute("userPassword");
%>

<p>firstWordId: <%=firstWordId%></p>
<p>wordMap: <%=wordMap%></p>
<p>maxId: <%=maxId%></p>
<p>rightAnswers: <%=rightAnswers%></p>
<p>wrongAnswers: <%=wrongAnswers%></p>
<p>repeatBy: <%=repeatBy%></p>
<p>id: <%=id%></p>
<p>pathReturn: <%=pathReturn%></p>
<p>reverse: <%=reverse%></p>
<p>domain: <%=request.getServerName()%></p>
<p>port: <%=request.getServerPort()%></p>
<p>errorMessage: <%=errorMessage%></p>
<p>countOfAllWords: <%=countOfAllWords%></p>
<p>countOfGuessedWords: <%=countOfGuessedWords%></p>
<p>nameOfTargetList: <%=nameOfTargetList%></p>

</body>
</html>
