<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 3/31/16
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <s:form action="execute" method="post">
        <s:textfield name="users[0].name" label="name"></s:textfield>
        <s:textfield name="users[1].name" label="name"></s:textfield>
        <s:submit value="OK" />
    </s:form>

</body>
</html>
