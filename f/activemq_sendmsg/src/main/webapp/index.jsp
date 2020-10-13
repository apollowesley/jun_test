<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<!DOCTYPE html>

<html>
    <head>
    	<title>hello</title>
    </head> 
    <body>
    	<h2>hello！</h2>
    	
    	<a href="<c:url value="/login/toLogin"/>">去登录，测试shiro权限认证吧</a>
    </body>
</html>