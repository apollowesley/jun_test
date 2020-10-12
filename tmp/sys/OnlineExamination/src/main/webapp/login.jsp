<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>D3登录界面</title>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function restF() {
		document.getElementById("yzmimg").setAttribute("src", "verification.jsp?" + new Date().getTime());
	}
</script>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<img src="images/login_logo.png" />
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<!--  
						<li><a href="#">设为首页</a></li>
						<li><a href="http://bbs.dwzjs.com">反馈</a></li>
						<li><a href="doc/dwz-user-guide.pdf" target="_blank">帮助</a></li>
						-->
					</ul>
				</div>
				<h2 class="login_title">
					<img src="images/login_title.png" />
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="login.do" method="post">
					<p>
						<label>用户名：</label> 
						<input type="text" id="username" name="username" value="${uname }" size="20" class="login_input" />
					</p>
					<p>
						<label>密码：</label> 
						<input type="password" id="password" name="password" size="20" class="login_input" />
					</p>
					<p>
						<label>验证码：</label> 
						<input class="code" type="text" id="yzm" name="yzm" style="width: 25%;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="4"/>
						<img id="yzmimg" alt="" src="verification.jsp" onclick="restF();" />
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value="" />
					</div>
					<div style="width: 100%;">
						<br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red;">${msg }</span>
					</div>
				</form>
			</div>
			<div class="login_banner">
				<img src="images/login_banner.png" />
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2014  成都工业学院.
		</div>
	</div>
</body>
</html>