<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="${ctx}/static/images/favicon.ico">

<title>欢迎登录</title>

<link href="${ctx}/static/bootstrap/css/bootstrap.min.css?${version_css}" type="text/css" rel="stylesheet" />
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}

.login-error {
	color: #C7254E;
	font-size: 90%;
	display: block;
	white-space: nowrap;
}
</style>
</head>
<body>
	<div class="container">
		<form action="${ctx }/login" class="form-signin" id="userCommand" method="POST">
			<h3 class="form-signin-heading">登录后台管理系统</h3>
			<input type="text" class="form-control input-block-level" id="username" name="username" placeholder="用户" value="wangxin">
			<input type="password" class="form-control input-block-level" id="password" name="password" placeholder="密码" value="123456">
			<span cssClass="login-error"></span>
			<button class="btn btn-large btn-primary" type="submit">登录</button>
			</br>
			</br>
		</form>
	</div>
</body>
</html>
