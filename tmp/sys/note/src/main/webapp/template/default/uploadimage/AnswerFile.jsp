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
	<jsp:useBean id="upload1" class="com.uploadimage.FileUpload" />
	<script>
	<% ServletInputStream in = request.getInputStream();
			upload1.setInputStream(in);
			String uid=session.getAttribute("UOID").toString();
			String uname=session.getAttribute("UName").toString();
			String filename = upload1.getFileUpload(uid,uname,"image/images/");
			
			String[] filenamesplit = filename.split("\\.");// 要求只能包含一个.，用于生成small文件名
			String filenamesmall = filenamesplit[0] + "small." + filenamesplit[1];
			
			out.println("var filename='" + filename + "';");
			out.println("var filenamesmall='" + filenamesmall + "';");
			in.close();%>
		parent.callbackt(filename,filenamesmall);
	</script>
</body>
</html>

