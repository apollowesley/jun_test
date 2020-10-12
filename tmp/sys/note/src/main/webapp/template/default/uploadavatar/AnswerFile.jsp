<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
</head>
<body>
	<jsp:useBean id="upload" class="com.avatarUpload.FileUpload" />
	<script>
	<% ServletInputStream in = request.getInputStream();
			upload.setInputStream(in);
			String filename = upload.getFileUpload();
			out.println("var filename='" + filename + "';");
			in.close();%>
		parent.callbackt(filename);
	</script>
</body>
</html>

