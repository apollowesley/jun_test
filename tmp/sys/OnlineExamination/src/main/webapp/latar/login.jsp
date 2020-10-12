<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线考试系统后台登陆</title>
<link href="themes/css/login.css" rel="stylesheet" type="text/css" />
<link href="themes/default/style.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="themes/css/core.css" rel="stylesheet" type="text/css"
	media="screen" />

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	//验证码变换
	function changecode(img) {
		//alert("空!!!");
		img.src = img.src + "?" + new Date().getTime();
	}
</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="#"><img src="themes/default/images/login_logo.png" /></a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#">设为首页</a></li>
						<li><a href="#">反馈</a></li>
						<li><a href="#" target="_blank">帮助</a></li>
					</ul>
				</div>
				<h2 class="login_title">
					<p>在线考试系统后台登陆</p>
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="LoginAction_doLogin" method="post">
					<input type="hidden" name="loginType" value="latar" /> <font
						color="red"><s:actionerror /></font>
					<!--显示错误信息 -->
					<p>
						<label>用户名：</label> <input type="text" name="name"
							value="${name}" class="login_input required" required />
					</p>
					<p>
						<label>密码：</label> <input type="password" name="password"
							size="20" class="login_input required" required /> 
					</p>
					<p>
						<label>验证码：</label> <input class="code" name="securityCode"
							type="text" size="5" required /> <span><img
							src="SecurityCodeImageAction_latarValidate" alt="" width="75"
							height="24" onclick="changecode(this)" /></span>
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner">
				<img src="themes/default/images/login_banner.jpg" />
			</div>
			<div class="login_main">
				<div class="login_inner">
					<p>您可以使用系统创建试卷</p>
					<p>查看学生完成情况</p>
					<p>对学生的作答进行统计等</p>
				</div>
			</div>
		</div>
		<div id="l-extra">
			<!------ extra ----->
			<p>Copyright © 2014 成都工业学院</p>
		</div>
	</div>
</body>
</html>