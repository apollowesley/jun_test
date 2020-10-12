<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="com.jake.bean.*,com.jake.dao.*,com.jake.util.*,com.jake.admin.*"%>

<%
	DBC dbc = new DBC();
	Admin admin = dbc.getAdminById(1);
%>
<html>
<head>
<title>添加留言<%=admin.getWebtitle()%></title>
<script type="text/javascript" src="scripts/validation.js"></script>
</head>
<body>
	<%@ include file="head.jsp"%>

	<div id="main" align="center">
		<h4>
			<a href="/guestbook01/index.jsp">返回首页</a>
		</h4>
		<form name="form1" method="post" action="/guestbook01/servlet/addMess">
			<table width="724" height="246" border="0">
				<tr>
					<th scope="row"><p>姓名：</p>
					</th>
					<td><label for="qq"></label> <label for="email"></label> <label
						for="textfield3"></label> <label for="name"></label> <input
						type="text" name="name"> <label for="title"><strong>性别：
								<input name="sex" type="radio" id="radio" value="0" checked>
						</strong> 男 <input type="radio" name="sex" id="radio2" value="1"> 女
					</label>
					</td>
				</tr>
				<tr>
					<th scope="row">标题：</th>
					<td><input type="text" name="title"> <strong>心情：
							<input name="mood" type="radio" id="radio3" value="0" checked>
							<img src="images/mood/0.jpg" width="50" height="50" /><input
							type="radio" name="mood" id="radio4" value="1"> <img
							src="images/mood/1.jpg" width="50" height="50" /> <input
							type="radio" name="mood" id="radio5" value="2"> <img
							src="images/mood/2.jpg" width="50" height="50" /> </strong>
					</td>
				</tr>
				<tr>
					<th scope="row"><p>内</p>
						<p>容</p>
					</th>
					<td><label for="content"></label> <textarea name="content"
							id="content" cols="45" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row">邮箱：</th>
					<td><input type="text" name="email" id="email">
					</td>
				</tr>
				<tr>
					<th scope="row">QQ：</th>
					<td><input type="text" name="qq" id="qq"> <strong>头像：
							<input name="heading" type="radio" id="radio6" value="1" checked>
							<img src="images/heading/1.jpg" width="50" height="50" /><input
							type="radio" name="heading" id="radio7" value="2"><img
							src="images/heading/2.jpg" width="50" height="50" /><input
							type="radio" name="heading" id="radio8" value="3"> <img
							src="images/heading/3.jpg" width="50" height="50" /> <input
							type="radio" name="heading" id="radio9" value="4"> <img
							src="images/heading/4.gif" width="50" height="50" /><input
							type="radio" name="heading" id="radio10" value="5"><img
							src="images/heading/5.gif" width="50" height="50" /> </strong></td>
				</tr>
				<tr>
					<th scope="row">&nbsp;</th>
					<td><input type="submit" name="submit" id="submit" value="留言">
						<input type="reset" name="reset" id="reset" value="重填">
					</td>
				</tr>
			</table>
		</form>
		<h2>&nbsp;</h2>
	</div>


	<%@ include file="foot.jsp"%>
</body>
</html>
