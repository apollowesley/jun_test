<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page
	import="com.jake.bean.*,com.jake.dao.*,com.jake.util.*,com.jake.admin.*"%>
<%!int pageNum;
	String path = "/guestbook01/admin/secure/list.jsp";%>
<%
	if (request.getParameter("pageNum") == null) {
		pageNum = 1;
	} else {
		pageNum = Integer.parseInt(request.getParameter("pageNum"));
	}
%>
<%
	DBC dbc = new DBC();
	Admin admin = dbc.getAdminById(1);
	MessageService messService = new MessageService();
	PageService pageservice = new PageService();
	messService.setPageSize(3);
	int total = messService.getTotalPage();
	ArrayList<Message> current = messService.getPage(pageNum);
%>
<html>
<head>
<title>首页<%=admin.getWebtitle()%></title>
</head>
<body>
	<div id="head" align="center">
		<img src="../../images/head.jpg" />
	</div>
	<div id="na" align="center">
		<h5 align="right">
			管理员主页:<%=admin.getAdminhttp()%>&nbsp;&nbsp;管理员QQ：<%=admin.getAdminqq()%>&nbsp;&nbsp;管理员Email：<%=admin.getAdminmail()%></h5>
		<h4>
			<a href="/guestbook01/admin/admin?q=logout">退出登录</a>
		</h4>
	</div>
	<div id="main" align="center">
		<%
			for (int i = 0; i < current.size(); i++) {
				Message mess = current.get(i);
		%>
		<table width="932" height="128" border="1" align="center">
			<tr>
				<td width="190" height="122">
					<%
						if (mess.getHeading() >= 4) {
					%> <img src="../../images/heading/<%=mess.getHeading()%>.gif" alt="头像"
					name="heading" width="100" height="100" align="left"
					style="background-color: #CCCC00"> <%
 	} else {
 %><img src="../../images/heading/<%=mess.getHeading()%>.jpg" alt="头像"
					name="heading" width="100" height="100" align="left"
					style="background-color: #CCCC00"> <%
 	}
 %>
					<table width="90" height="74" border="0">
						<tr>
							<td width="43">性别：</td>
							<td width="37"><img name="sex"
								src="../../images/sex/<%=mess.getSex()%>.jpg" width="40" height="40"
								alt="" style="background-color: #0000FF">
							</td>
						</tr>
						<tr>
							<td>心情：</td>
							<td><img name="mood"
								src="../../images/mood/<%=mess.getMood()%>.jpg" width="40" height="40"
								alt="" style="background-color: #990033">
							</td>
						</tr>
					</table>
				</td>
				<td width="629"><table width="633" height="126" border="0">
						<tr>
							<td width="51" height="28" bgcolor="#FFFFFF"><em><strong>姓名：</strong>
							</em></td>
							<td width="515"><%=mess.getName()%></td>
							<td width="38"><a
								href="/guestbook01/admin/secure/edit.jsp?id=<%=mess.getId()%>">编辑</a>
							</td>
						</tr>
						<tr>
							<td height="34" bgcolor="#FFFFFF"><em><strong>主题：</strong>
							</em></td>
							<td><%=mess.getTitle()%></td>
							<td><a
								href="/guestbook01/admin/admin?q=delete&id=<%=mess.getId()%>">删除</a>
							</td>
						</tr>
						<tr>
							<td height="30" bgcolor="#FFFFFF"><em><strong>内容：</strong>
							</em></td>
							<td><%=mess.getContent()%></td>
							<td><a
								href="/guestbook01/admin/secure/rep.jsp?id=<%=mess.getId()%>">回复</a>
							</td>
						</tr>
			  </table></td>
			</tr>
		</table>
		<hr>
	</div>


	<%
		}
	%>

	<div align="center" id="page">
		<%=pageservice.getPage(pageNum, total, path)%>
	</div>
	<%@ include file="../../foot.jsp"%>
</body>
</html>
