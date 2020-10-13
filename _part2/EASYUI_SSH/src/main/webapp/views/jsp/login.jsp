<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<title>登录</title>
<link href="css/bootstrap.min.css" title="" rel="stylesheet" />
<link title="orange" href="css/login.css" rel="stylesheet" type="text/css"/>
</head>

<body>
  
  <div style="height:1px;"></div>
  <div class="login">
     <header>
	    <h1>登录</h1>
	 </header>
	 <div class="sr" >
	    <form action='/login'>
		    <div class="name">
				<label>
				<i class="sublist-icon glyphicon glyphicon-user"></i>
				</label>
				<input type="text"  placeholder="这里输入登录名" class="name_inp">
			</div>
			 <div class="name">
				<label>
				<i class="sublist-icon glyphicon glyphicon-pencil"></i>
				</label>
				<input type="text"  placeholder="这里输入登录密码" class="name_inp">
			</div>
			<button class="dl" >登录</button>
		</form>
	 </div>
  </div>

<div style="text-align:center;">
		<div class="footer">版权所有@ 958735396@qq.com</div>
</div>
</body>
</html>
