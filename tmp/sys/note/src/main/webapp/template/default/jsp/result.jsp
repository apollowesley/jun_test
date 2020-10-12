<%@ page language="java" import="java.util.*,com.hibernate.voDao.users" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>此页面没有用到 - ${websiteName}</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
body{font-size:14px;font-family:宋体;margin:auto;text-align:center;width:960px;}
table{border:1px solid #aaa;border-collapse:collapse;text-align:center;margin:auto;}
table tr td{border:1px solid #aaa;padding:0 3px 0 3px;}
p{text-indent: 2em;text-align:left;}
</style>
  </head>
  
  <body>
<p>这是一个jsp+servlet组成的项目，servlet通过hibernate从数据库中取出数据，通过request返回到jsp页面，页面通过java标签循环显示！此项目是测试之用，只添加了hibernate，并没有spring和struts，只是为了测试hibernate和servlet的使用。</p>
    <table>
		<tr>
			<td>UID</td>
			<td>用户名</td>
			<td>密码</td>
			<td>用户姓名</td>
			<td>身份证号</td>
		</tr>
		<%
			List list=(List)request.getAttribute("list");
			users user;
			for(int i=0;i <list.size();i++)
			{
			user=(users)list.get(i);
		%>
		<tr>
			<td><%=user.getUid()%></td>
			<td><%=user.getUsername()%></td>
			<td><%=user.getPassword()%></td>
			<td><%=user.getRealname()%></td>
			<td><%=user.getIdentityCardNumber()%></td>
		</tr>

		<%
			}
		%>
	</table>
  </body>
</html>
