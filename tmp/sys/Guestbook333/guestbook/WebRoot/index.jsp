<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page
	import="com.jake.bean.*,com.jake.dao.*,com.jake.util.*,com.jake.admin.*"%>
<%!int pageNum;
	String path = "/guestbook01/index.jsp";%>
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
	<%@ include file="head.jsp"%>
	<div id="na" align="center">
		<h5 align="right">
			管理员主页:<%=admin.getAdminhttp()%>&nbsp;&nbsp;管理员QQ：<%=admin.getAdminqq()%>&nbsp;&nbsp;管理员Email：<%=admin.getAdminmail()%></h5>
		<h4>
			<a href="/guestbook01/add.jsp">添加留言</a>
		</h4>
	</div>
	<div id="main" align="center">
		<%
			for (int i = 0; i < current.size(); i++) {
				Message mess = current.get(i);
		%>
		<table width="991" height="244" border="1" align="center">
			<tr>
				<td width="100" height="238">
					<%
						if (mess.getHeading() >= 4) {
					%> <img
					src="images/heading/<%=mess.getHeading()%>.gif" alt="头像"
					name="heading" width="100" height="100" align="left"
					style="background-color: #CCCC00"> <%
 	} else {
 %><img src="images/heading/<%=mess.getHeading()%>.jpg" alt="头像"
					name="heading" width="100" height="100" align="left"
					style="background-color: #CCCC00">
					<%
						}
					%>
					<table width="90" height="74" border="0">
						<tr>
							<td width="43">性别：</td>
							<td width="37"><img name="sex"
								src="images/sex/<%=mess.getSex()%>.jpg" width="40" height="40"
								alt="" style="background-color: #0000FF"></td>
						</tr>
						<tr>
							<td>心情：</td>
							<td><img name="mood"
								src="images/mood/<%=mess.getMood()%>.jpg" width="40"
								height="40" alt="" style="background-color: #990033"></td>
						</tr>
					</table></td>
				<td width="725"><table width="655" height="94" border="0">
						<tr>
							<td width="51" height="21" bgcolor="#FFFFFF"><em><strong>姓名：</strong>
							</em>
							</td>
							<td width="567"><%=mess.getName()%></td>
						</tr>
						<tr>
							<td height="21" bgcolor="#FFFFFF"><em><strong>主题：</strong>
							</em>
							</td>
							<td><%=mess.getTitle()%></td>
						</tr>
						<tr>
							<td height="21" bgcolor="#FFFFFF"><em><strong>内容：</strong>
							</em>
							</td>
							<td><%=mess.getContent()%></td>
						</tr>
						<tr>
							<td height="21" bgcolor="#FFFFFF">&nbsp;</td>
							<td bgcolor="#FFFFFF">
								<h6>
									留言时间：<%=mess.getPubtime()%>&nbsp;&nbsp;&nbsp;&nbsp;QQ：<%=mess.getQq()%>&nbsp;&nbsp;&nbsp;&nbsp;邮箱：<%=mess.getEmail()%></h6>
							</td>
						</tr>
					</table>
					<p>
						<%
							if (mess.getRepcontent() != null) {
						%>
					</p>
					<table width="424" height="48" border="0">
						<tr>
							<td width="118" bgcolor="#CC6699">管理员回复：</td>
							<td width="332"><%=mess.getRepcontent()%></td>
						</tr>
						<tr>
							<td bgcolor="#FFFFFF">&nbsp;</td>
							<td><h6>
									回复时间：<%=mess.getReptime()%></h6>
							</td>
						</tr>
					</table> <%
 	}
 %>
				</td>
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
	<%@ include file="foot.jsp"%>
</body>
</html>
