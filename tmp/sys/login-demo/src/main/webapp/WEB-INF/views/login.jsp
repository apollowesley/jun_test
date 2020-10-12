<%--
  Created by IntelliJ IDEA.
  User: ASUSS
  Date: 2016/9/13
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>
</head>
<body>
<s:form action="login">
    <s:textfield name="user.userName" label="用户名"></s:textfield>
    <s:password name="user.password" label="密码"></s:password>

    <s:submit value="注册"></s:submit>
</s:form>
</body>
</html>
