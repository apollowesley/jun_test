<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 9:21 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ImplicitObjects</title>
</head>
<body>
<b>Http headers:</b><br/>
<%
    for (final Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
        final String header = e.nextElement();
        out.println(header + ": " + request.getHeader(header) + "<br/>");
    }
%>
<hr/>
<%
    out.println("Buffer size: " + response.getBufferSize() + "</br>");
    out.println("Session id: " + session.getId() + "<br/>");
    out.println("Servlet name: " + config.getServletName() + "<br/>");
    out.println("Server info: " + application.getServerInfo());
%>
</body>
</html>
