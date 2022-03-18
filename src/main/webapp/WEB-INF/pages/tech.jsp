<%@ page import="com.revision.entity.User" %>
<%@ page import="com.revision.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    String userName = user.getUserName();

    UserService um = new UserService();
    int countOfWords = um.getCountOfWords(user.getId());
    int countOfDictionaries = um.getCountOfDictionaries(user.getId());

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
