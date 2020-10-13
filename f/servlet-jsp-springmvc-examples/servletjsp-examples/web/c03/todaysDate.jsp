<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 8:58 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Date</title>
</head>
<body>
<%
    final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    final String s = dateFormat.format(new Date());
    out.println("Today is " + s);
%>
</body>
</html>
