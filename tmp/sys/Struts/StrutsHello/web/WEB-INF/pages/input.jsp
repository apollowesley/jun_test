<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 2/26/16
  Time: 09:33
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
<form action="<%=path %>/product/save.action" method="post">
    name:<input type="text" name="product.name"/>
    <input type="submit"/>
</form>
</body>
</html>
