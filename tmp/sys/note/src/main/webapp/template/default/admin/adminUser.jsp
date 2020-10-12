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
    
    <title>后台管理用户界面  - ${websiteName}</title>
<link href="${templatePath}/css/home.css" rel="stylesheet" type="text/css"/>
<style>
table th{border-bottom:0.5px dotted;padding:3px;margin:3px;line-height:23px;height:23px;font-weight:700;}
table td{border-bottom:0.5px dotted;padding:3px;margin:3px;line-height:23px;height:23px;}
tbody tr.split{background-color:#fbfbfb;}
table a{color:#33f;padding:3px;margin:3px;}
.uid{width:40px;}
.username{width:90px;}
.adminid{width:50px;}

</style>
</head>
<body>
<table>
<tr class="split"><th></th><th>UID</th><th>用户名</th><th>管理员</th><th></th></tr>
<c:forEach items="${list}" var="list" varStatus="status"><tr <c:if test="${status.index%2!=0}">class="split"</c:if>>
          <td></td>
          <td class="uid">${list.uid}</td>
          <td class="username">${list.username}</td>
          <td class="adminid">${list.adminid==true?"是":"否"}</td>
          <td><a href="Admin.yy?a=users&operate=detail&uid=${list.uid}">详情</a>
          <a href="Admin.yy?a=users&operate=delete&uid=${list.uid}" onclick="if(confirm('删除后不可恢复！！！')){return true;}return false;">删除</a>
          </td>
      </tr></c:forEach>
</table>
</body>