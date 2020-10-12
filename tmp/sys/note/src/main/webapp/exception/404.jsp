<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>您要找的页面不见了 - 微我</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
</head>

<body>
<%@include file="/template/default/jsp/global_topbar.jsp" %>
	<script type="text/javascript" src="http://www.qq.com/404/search_children.js" charset="utf-8"></script>
<%@include file="/template/default/jsp//global_footer.jsp" %>
<%@include file="/template/default/html/modbacktop.html" %>
</body>
</html>
