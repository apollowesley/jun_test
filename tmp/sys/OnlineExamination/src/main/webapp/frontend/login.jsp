<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>login</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/login.css" />
<link rel="stylesheet" href="css/reset.css" />
<script type="text/javascript">
	//验证码变换
	function changecode(img) {
		img.src = img.src + "?" + new Date().getTime();
	}
	function changecode2() {
		//alert("空!!!");
		document.getElementById("huan").src = document.getElementById("huan").src
				+ "?" + new Date().getTime();

	}
</script>
</head>
<body>

	<div class="navbar navbar-fixed-top">
		<!---------------- navbar-fixed-top ----------------->
		<div class="backcolor">
			<div class="container">
				<div class="head_logo">
					<img src="images/school5.png" />
				</div>
				<div class="title">
					<h1 style="color: #FFF; text-align: center;">在线考试系统</h1>
				</div>
			</div>
			<!-- /container -->

		</div>
		<!-- /backcolor -->
	</div>
	<!-- /navbar -->
	<div id="l-container">
		<!----------------  -container  ----------------->
		<div id="login-header">
			<!------ header ----->
			<h3>用户登录</h3>
		</div>
		<!-- /login-header -->

		<div id="l-content" class="clearfix">
			<!------ content ----->

			<form action="FrontendLoginAction_doLogin" method="post">
				<fieldset>
					<font color="red"><s:actionerror /></font>
					<!--显示错误信息 -->
					<div class="control-group">
						<label class="control-label" for="username">邮箱</label>
						<div class="controls">
							<input type="text" class="" id="email" name="emailAddress"
								value="${emailAddress}" required>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">密码</label>
						<div class="controls">
							<input type="password" class="" id="password" name="password"
								pattern="^[A-Za-z0-9]+$" placeholder="请输入密码(只能是数字和英文)"
								oninvalid="setCustomValidity('只能输入数字和英文')"
								oninput="setCustomValidity('')" required>
						</div>
					</div>
					<div>
						<div style="float: left;">
							<label class="code" for="security">验证码</label> <input type="text"
								name="securityCode" style="width: 60%; float: right;" required />
						</div>
						<div style="float: right;">
							<a class="change" onclick="changecode2()">换一张</a> <img id="huan"
								src="SecurityCodeImageAction_frontendValidate" width="100"
								height="200" id="Verify" style="cursor: pointer;" alt="看不清，换一张"
								onclick="changecode(this)" />


						</div>
					</div>
				</fieldset>

				<div id="remember-me" class="pull-left">
					<input type="checkbox" name="remember" id="remember" /> <label
						id="remember-label" for="remember">记住登录</label>
				</div>

				<div class="pull-right">
					<a href="#">忘记密码？</a>
				</div>
				<div class="buttons">
					<input type="submit" value="登录" class="btn btn-primary btn_blue" />
					<a href="RegAction_toRegPage">
						<button type="button" class="btn btn-danger btn_red">立即注册</button>
					</a>

				</div>
			</form>

		</div>
		<!-- /l-content -->


		<div id="l-extra">
			<!------ extra ----->
			<p>Copyright © 2014 成都工业学院</p>
		</div>
		<!-- /l-extra -->

	</div>
	<!-- /l-container-->

	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>