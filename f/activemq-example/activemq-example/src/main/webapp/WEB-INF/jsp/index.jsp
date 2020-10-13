<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring JMS</title>
</head>
<body>
	<h1>消息发送</h1>
	<h3>消息: ${message}</h3>
	<a href="${ctx}/send1">send1</a>
	<a href="${ctx}/send2">send2</a>
	<a href="${ctx}/send3">send3</a>
</body>
</html>