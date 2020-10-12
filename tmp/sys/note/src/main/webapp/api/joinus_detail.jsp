<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>${joinAsAPartner.jaaptitle}</title>
<meta name="viewport" content="width=device-width,initial-scale = 1 ,minimum-scale = 1 ,maximum-scale = 2,user-scalable = yes ,target-densitydpi = device-dpi" />
<style>
body {
	font-size: 32px;
}
</style>
</head>
<body>

${joinAsAPartner.jaapcontent}
</body>
</html>
