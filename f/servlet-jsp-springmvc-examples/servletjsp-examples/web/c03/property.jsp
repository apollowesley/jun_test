<%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 10:35 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Property</title>
</head>
<body>
<jsp:useBean id="employee" class="fun.oook.c03.Employee"/>
<jsp:setProperty name="employee" property="firstName" value="Joey"/>

First Name:
<jsp:getProperty name="employee" property="firstName"/>

<br>
<br>
<br>
<jsp:include page="copyright.jspf"/>
</body>
</html>
