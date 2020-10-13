<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7 ]><html lang="en" class="ie6 ielt7 ielt8 ielt9"><![endif]--><!--[if IE 7 ]><html lang="en" class="ie7 ielt8 ielt9"><![endif]--><!--[if IE 8 ]><html lang="en" class="ie8 ielt9"><![endif]--><!--[if IE 9 ]><html lang="en" class="ie9"> <![endif]--><!--[if (gt IE 9)|!(IE)]><!--> 
<html lang="en"><!--<![endif]--> 
	<head>
		<meta charset="utf-8">
		<title>用户登录</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="${ctx}resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="${ctx}resources/css/bootstrap-responsive.min.css" rel="stylesheet">
		<link href="${ctx}resources/css/site.css" rel="stylesheet">
		<!--[if lt IE 9]><script src="js/html5.js"></script><![endif]-->
	</head>
	<body>
		<div id="login-page" class="container">
			<h1>用户登录</h1>
			<form id="login-form" class="well" action="jsp/doLogin.do">
			<input type="text" class="span2" placeholder="Email" /><br />
			<input type="password" class="span2" placeholder="Password" /><br />
			<button type="submit" class="btn btn-primary">登录</button>
		</form>	
		</div>
		<script src="${ctx}resources/js/jquery.min.js"></script>
		<script src="${ctx}resources/js/bootstrap.min.js"></script>
		<script src="${ctx}resources/js/site.js"></script>
	</body>
</html>