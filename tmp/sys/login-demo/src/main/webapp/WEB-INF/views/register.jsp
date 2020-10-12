<%--
  Created by IntelliJ IDEA.
  User: ASUSS
  Date: 2016/9/10
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户登录</title>
</head>
<body>
<%--<form action="register.action" method="post">
    userName:<input type="text" name="user.userName"/><br/>
    password:<input type="password" name="user.password"/><br/>
    <input type="submit" value="register">
</form>--%>

    <s:form action="register">
        <s:textfield name="user.userName" label="用户名"></s:textfield>
        <s:password name="user.password" label="密码"></s:password>

        <s:submit value="注册"></s:submit>
    </s:form>

</body>
</html>
