<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 9:46 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Scriptlet</title>
</head>
<body>
<%-- first scriptlet --%>
<%
    for (final Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
        final String header = e.nextElement();
        out.println(header + ": " + request.getHeader(header) + "<br/>");
    }
    final String message = "Thank you.";
%>
<hr>
<%-- second scriptlet --%>
<%
    out.println(message);
%>
<hr>
<%-- 表达式 --%>
<%=message%>
</body>
</html>
