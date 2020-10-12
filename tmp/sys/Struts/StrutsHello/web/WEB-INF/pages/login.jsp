<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 2/26/16
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<%=path %>/user/login.action" method="post">
    Username:<input type="text" name="user.name"/>
    <input type="submit"/>
</form>
</body>
</html>
