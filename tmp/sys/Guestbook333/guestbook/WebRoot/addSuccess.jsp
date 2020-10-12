<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加留言成功</title>
</head>

<body>
	<h3 align="center">
		添加留言成功，谢谢。<a href="/guestbook01/index.jsp">查看留言</a><a
			href="/guestbook01/add.jsp">   继续添加留言</a>
	</h3>
</body>
</html>
