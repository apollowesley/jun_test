<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="com.jake.bean.*,com.jake.dao.*,com.jake.util.*,com.jake.admin.*"%>
<%
	DBC dbc = new DBC();
	Admin admin = dbc.getAdminById(1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>管理员登录<%=admin.getWebtitle() %></title>
  </head>
  <body>
<div align="center"><img src="../images/head.jpg"/></div>
<div align="center" id="main">
  <h2>管理员登录</h2>
  <form name="form1" method="post" action="/guestbook01/admin/admin?q=login">
    <table width="305" height="96" border="0">
      <tr>
        <th scope="row">用户名：</th>
        <td><label for="adminname"></label>
        <input type="text" name="adminname" id="adminname"></td>
      </tr>
      <tr>
        <th scope="row">密码：</th>
        <td><label for="adminpass"></label>
        <input type="text" name="adminpass" id="adminpass"></td>
      </tr>
      <tr>
        <th scope="row">&nbsp;</th>
        <td><input type="submit" name="submit" id="submit" value="  登  录  ">
        <input type="reset" name="reset" id="reset" value="  取  消  "></td>
      </tr>
    </table>
  </form>
</div>

<%@include file="../foot.jsp" %>
  </body>
</html>
