<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 2/26/16
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
error occured!
<s:debug></s:debug>

Exception: <s:property value="exception" /><br />
Exception Stack:<s:property value="exceptionStack"/>

</body>
</html>
