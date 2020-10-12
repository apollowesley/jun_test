<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>register</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/register.css" /> 
    <link rel="stylesheet"  href="css/reset.css" />
</head>
<body>

	<div class="navbar navbar-fixed-top">
		<div class="backcolor">	
            <div class="container">
            	<div class="head_logo"><img src="images/school5.png" /></div>
                <div class="title"><h1 style="color:#FFF; text-align:center;">在线考试系统</h1></div>
            </div> <!-- /container -->
            
        </div> <!-- /backcolor -->
	</div>
	<!-- /navbar -->
	<div id="register-container">
		<div id="register-header">
			<h3>用户注册</h3>
		</div>
		<!-- /login-header -->

		<div id="register-content" class="clearfix">

			<form action="RegAction_doReg" method="post" >
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="username">邮箱</label>
					<div class="controls">
						<input type="text" name="emailAddress" placeholder="请输入登录邮箱"
							class="" id="username" value="${emailAddress}" required>
						<font color="red" class="fonterror"><s:fielderror>
								<s:param>emailAddress</s:param>
							</s:fielderror></font>
						<p class="help-block muted helpe_font_small">请输入有效的邮箱！</p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="password">密码</label>
					<div class="controls">
						<input type="password" name="password" placeholder="请输入密码"
							class="" id="password"  pattern="^[A-Za-z0-9]+$" placeholder="请输入密码(只能是数字和英文)" oninvalid="setCustomValidity('只能输入数字和英文')" oninput="setCustomValidity('')" required> <font color="red"
							class="fonterror"><s:fielderror>
								<s:param>password</s:param>
							</s:fielderror></font>
						<p class="help-block muted helpe_font_small">请输入6-16位密码，区分大小写，不能使用空格</p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="password">确认密码</label>
					<div class="controls">
						<input type="password" name="confirmPassword"
							placeholder="请在次输入密码" class="" id="confirmPassword"  pattern="^[A-Za-z0-9]+$" placeholder="请输入密码(只能是数字和英文)" oninvalid="setCustomValidity('只能输入数字和英文')" oninput="setCustomValidity('')" required>
						<p class="help-block muted helpe_font_small">请输入6-16位密码，区分大小写，不能使用空格</p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="name">姓名</label>
					<div class="controls">
						<input type="text" name="userName" placeholder="请输入中文姓名" class=""
							id="name" /> <font color="red" class="fonterror"><s:fielderror>
								<s:param>nickName</s:param>
							</s:fielderror></font>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="age">年龄</label>
					<div class="controls">
						<input type="text" name="userAge" class="" id="age" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="unit">单位</label>
					<div class="controls">
						<input type="text" name="userUnit" class="" id="unit" />
					</div>
				</div>
			</fieldset>

			<div class="pull-right">
				已有账号，<a href="FrontendLoginAction_toLoginPage">去登录</a>
			</div>
			<div class="buttons">

				<input type="submit" value="注册" class="btn btn-success btn_green" />

			</div>
			</form>

		</div>
		<!-- /login-content -->


		<div id="register-extra">
			<p>Copyright © 2014 成都工业学院</p>
		</div>
		<!-- /login-extra -->

	</div>
	<!-- /login-container-->
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>