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
<base href="<%=basePath%>">

<title>login.jsp</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<!-- 登录表单 -->
	<form action="checkUser" method="post">
		<table>
			<tr>
				<td>名字</td>
				<td><input type="text" style="width: 100px" name="username" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" style="width: 100px" name="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="登录" /></td>
				<td><input type="reset" value="重新填写" /></td>
			</tr>
		</table>
	</form>
	
</body>
</html>
