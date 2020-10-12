<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 3/4/16
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
id:<s:property value="formUser.id"></s:property><br />
name:<s:property value="formUser.name"></s:property><br />
desc:<s:property value="formUser.desc"></s:property><br />
accept:<s:if test="formUser.accept">Yes</s:if><br /><!-- 判断formUser.accept是否为true -->
</body>
</html>
