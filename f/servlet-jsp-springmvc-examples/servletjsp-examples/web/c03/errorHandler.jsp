<%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 10:40 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>An Error has occurred.</h1>
<%
    if (exception == null) {
        out.println("exception message not found");
        return;
    }
    out.println(exception.toString());
%>
</body>
</html>
