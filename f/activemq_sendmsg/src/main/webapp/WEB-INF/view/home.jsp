<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系统首页</title>
<script src="<c:url value="/resources/js/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
</head>
<body>
    <h2>欢迎你,<shiro:principal/></h2><a href="<c:url value="/login/logout"/>">退出系统</a>
    <h2>这是系统首页！你登录成功了！</h2>
    
    <br/>
    <h3>拥有的权限：</h3>
    
    <shiro:hasPermission name="role:mgt">角色管理</shiro:hasPermission>       <br/>
    <shiro:hasPermission name="user:list">用户列表查看</shiro:hasPermission>  <br/>
    <shiro:hasPermission name="user:add">添加用户</shiro:hasPermission>      <br/>
    <shiro:hasPermission name="user:update">修改用户</shiro:hasPermission>   <br/>
    <shiro:hasPermission name="role:list">角色列表查看</shiro:hasPermission>  <br/>
</body>
</html>