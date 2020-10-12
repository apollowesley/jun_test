<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="com.jake.bean.*,com.jake.dao.*,com.jake.util.*,com.jake.admin.*"%>

<%
	DBC dbc = new DBC();
	Admin admin = dbc.getAdminById(1);
	String id = request.getParameter("id");
	Message mess = null;
	if (id != null) {
		mess = dbc.getMessById(Integer.parseInt(id));
	} else {
		session.setAttribute("error", "页面操作错误，请返回");
		response.sendRedirect("/guestbook01/admin/loginError.jsp");
	}
%>
<html>
<head>
<title>修改留言<%=admin.getWebtitle()%></title>
</head>
<body>
	<div id="head" align="center">
		<img src="../../images/head.jpg">
	</div>

	<div id="main" align="center">
		<h4>
			<a href="/guestbook01/admin/secure/list.jsp">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/guestbook01/admin/admin?q=logout">退出登录</a>
		</h4>
		<form name="form1" method="post"
			action="/guestbook01/admin/admin?q=edit">
			<input type="hidden" name="id" value="<%=id%>">
			<table width="466" height="246" border="0">
				<tr>
					<th width="97" scope="row"><p>
							<em>姓名：</em>
						</p></th>
					<td width="359"><input type="text" name="name" id="name"
						value="<%=mess.getName()%>">
					</td>
				</tr>
				<tr>
					<th scope="row"><em>标题：</em>
					</th>
					<td><input type="text" name="title" id="title"
						value="<%=mess.getTitle()%>">
					</td>
				</tr>
				<tr>
					<th scope="row"><p>
							<em>内</em>
						</p>
						<p>
							<em>容</em>
						</p></th>
					<td><textarea name="content" id="content" cols="45" rows="5"><%=mess.getContent()%></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row"><em>邮箱：</em>
					</th>
					<td><input type="text" name="email" id="email"
						value="<%=mess.getEmail()%>"></td>
				</tr>
				<tr>
					<th scope="row"><em>QQ：</em>
					</th>
					<td><input type="text" name="qq" id="qq"
						value="<%=mess.getQq()%>">
					</td>
				</tr>
				<tr>
					<th scope="row">&nbsp;</th>
					<td><input type="submit" name="submit" id="submit"
						value="  修  改  "> <input type="reset" name="reset"
						id="reset" value="  重  填  "></td>
				</tr>
			</table>
		</form>
		<h2>&nbsp;</h2>
	</div>


	<%@ include file="../../foot.jsp"%>
</body>
</html>
