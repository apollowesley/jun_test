<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台管理用户详细界面  - ${websiteName}</title>
<link href="${templatePath}/css/home.css" rel="stylesheet" type="text/css"/>
<style>
table td{border-bottom:0.5px dotted}
tbody tr.split{background-color:#fbfbfb;}
table tbody tr td{padding:3px;margin:3px;line-height:23px;height:23px;}
input{height:18px;line-height:18px;width:183px;}
.uid{width:60px;}
.submitbutton{height:21px;line-height:21px;width:47px;}
</style>
</head>
<body>
<form action="Admin.yy" method="post">
<input type="hidden" value="users" name="a"/>
<input type="hidden" value="${user.uid}" name="uid"/>
<input type="hidden" value="change" name="operate"/>
<table>
<tr><td class="uid">UID</td><td class="uid">${user.uid}</td></tr>
<tr><td>用户名</td><td class="username">${user.username}</td></tr>
<tr><td>管理员</td><td class="adminid">${user.adminid==true?"是":"否"}</td></tr>
<tr><td>邮箱</td><td><input type="text" name="email" value="${user.email}"/></td></tr>
<tr><td></td><td><input class="submitbutton" type="submit" value="提交"/></td></tr>
</table>
</form>
</body>