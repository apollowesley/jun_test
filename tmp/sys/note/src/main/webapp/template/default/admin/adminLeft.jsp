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
    
    <title>后台管理  - ${websiteName}</title>
<link href="${templatePath}/css/home.css" rel="stylesheet" type="text/css"/>
<link href="${templatePath}/admin/css/admin.css" rel="stylesheet" type="text/css"/>
<style>
</style>
<script src="${templatePath}/admin/js/jquery-2.0.3.min.js"></script>
<script  src="${templatePath}/admin/js/bill.js"></script>
</head>
  
<body>
<div class="header">
<img class="admin_img" src="${templatePath}/img/backstage.jpg" alt="记账，就像和朋友对话" title="记账，就像和朋友对话"/>
<div class="nav_div"></div>
  <div class="nav_tools"><a href="Admin.yy?a=logout">退出登录</a></div>
</div>
<div class="user_cent">
<div class="user_cent_l">
<ul>
<%-- <li><a target="main" href="Admin.yy?a=users">用户管理</a></li>
<li><a target="main" href="Admin.yy?a=feedback">反馈管理</a></li>
<li><a target="main" href="Admin.yy?a=template">模板</a></li> --%>
</ul>
</div>
<%--此位置添加right页面--%>
<div class="user_cent_r">
<div class="right_content">
    <div class="search_title">搜索和标题</div>
    <div class="tr_options">表格内容操作</div>
    <table id="myTable">
    <%-- <tr><th><input type="checkbox"/></th><th>uid</th><th>用户名</th><th>真实姓名</th><th>E-mail</th><th>头像</th><th>操作</th></tr>
    <tr><td><input type="checkbox"/></td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td></tr>
    <tr><td><input type="checkbox"/></td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td></tr>
    <tr><td><input type="checkbox"/></td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td></tr>
    <tr><td><input type="checkbox"/></td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td></tr> --%>
    </table>
</div>
</div>
</div>
</body>
</html>
