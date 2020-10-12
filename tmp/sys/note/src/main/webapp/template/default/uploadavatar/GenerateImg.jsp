<%@ page language="java"
	import="java.util.*,java.io.*,com.hibernate.voDao.*"
	pageEncoding="UTF-8"%>
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
	<jsp:useBean id="generate"
		class="com.avatarUpload.GenerateImg" />
	<script>
		
	<%String filename = request.getParameter("filename");
			String avatarfilename = request.getSession().getAttribute("UOID")
					.toString();
			String top = request.getParameter("top");
			top = top == "" ? "50" : top;
			String left = request.getParameter("left");
			left = left == "" ? "50" : left;
			String width = request.getParameter("width");
			width = width == "" ? "100" : width;
			String height = request.getParameter("height");
			height = height == "" ? "100" : height;
			String result = generate.generateImg(filename, avatarfilename, top,
					left, width, height);
			out.println("var result='" + result + "';");
			if (result.equals("success")) {
				UsersDAO usersDao = new UsersDAO();
				Users user = new Users();
				user = usersDao.findById(Integer.parseInt(avatarfilename));
				user.setAvatarstatus(true);
				usersDao.save(user);
			}%>
		parent.callbackg(result);
	</script>
	<div align="right">
		<a href="index.jsp">回到首页</a>
	</div>
</body>
</html>

