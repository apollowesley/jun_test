<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>信息管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <script src="${ctx}/static/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
 <script type="text/javascript">
 var error = "<%=request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME)%>";
 </script>
<style>
body {
	background: #d1f1fc url(${ctx}/static/images/body_bg.jpg) no-repeat
		right 0;
	font: tahoma, Arial, Helvetica, sans-serif;
	text-align: center;
	overflow: auto;
}

img {
	border: 0
}

h1 {
	font-size: 200%;
	margin: 0
}
p {
	margin: 0 0 1em 0
}

#login {
	position: absolute;
	top: 50%;
	left: 50%;
	width: 500px;
	height: 270px;
	margin: -135px 0 0 -250px;
	background: url(${ctx}/static/images/login_area.jpg) no-repeat 0 0;
	text-align: left;
	z-index: 9001;
}

#shadow {
	position: absolute;
	top: 50%;
	left: 50%;
	width: 500px;
	height: 270px;
	margin: -132px 0 0 -247px;
	z-index: 9000;
	filter: progid:DXImageTransform.Microsoft.alpha(opacity=50) progid:DXImageTransform.Microsoft.Blur(pixelradius=2);
	-moz-opacity: 0.5;
	opacity: .50;
	background-color: #777;
}

#about {
	position: absolute;
	bottom: 10px;
	left: 10px;
	cursor: pointer;
	font: 11px "Trebuchet MS";
	color: #5ab3f4;
}

#login h1 {
	margin-bottom: 10px;
	padding: 0px 0 0 10px;
	font: 22px/50px "微软雅黑", tahoma;
	text-indent: 60px;
	text-align: left;
}

#login form {
	height: 200px;
	padding-left: 100px;
	background: url(${ctx}/static/images/user.png) no-repeat 30px 30px;
}
#login form p {
	clear: both;
	margin: 5px 0;
}

#login form p img {
	vertical-align: middle;
}

#login form p label {
	width: 50px;
	display: inline-block;
	font: 10px "微软雅黑", tahoma;
	color: #246bb3;
}

#login_ctr {
	text-indent: 58px;
}

#login form p .logintxt {
	font: 12px/16px tahoma;
	height: 30px;
	border: 1px solid #ccc;
}

#login form p input.logintxt {
	padding: 0 2px;
}

#login img {
	cursor: hand;
}

.w1 {
	width: 230px;
}
</style>
</head>

 
<body  style="background:#d1f1fc url(${ctx}/static/images/body_bg.jpg) no-repeat right 0;">
	<div id="shadow" style="visibility: hidden;"></div>
	<div id="login">
		<h1 id='appTitle'> &nbsp;</h1>
		<form name="loginForm" id="loginForm" method="post" action="${ctx}/login">
			<div id='loginbody'>
				<p class="error_message" id="error_message">&nbsp;</p>

				<p>
					<label for='userId'></label> <input type="text" name="username"  
						id="username" class="logintxt w1" value="${username}" placeholder="用户名"/>
				</p>
				<p>
					<label for='pass'></label> <input type="password" name="password"
						id="password" class="logintxt w1"  placeholder="密码"/>
				</p>
				<p>
				<label class="checkbox" for="rememberMe" style="margin-left: 10px"></label><input 
						type="checkbox" id="rememberMe" name="rememberMe" s>  记住我</input>
				</p>
				<p id="login_ctr">
					<input type="image" src="${ctx}/static/images/login.jpg"  type="button" onclick="return checkValue();"/>
				</p>
			</div>
 
		</form>
	</div>
</body>
<script>
	!function() {
		//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		layer.ready(function() {
			if (error.indexOf("UnknownAccountException") > 0) {
				layer.tips('用户名错误', '#username', {
					tips : [ 2, 'red' ],
					time : 2000
				});
			} else if (error.indexOf("IncorrectCredentialsException") > 0) {
				layer.tips('密码错误', '#password', {
					tips : [ 2, 'red' ],
					time : 2000
				});
			} else {
				layer.tips('用户名或者密码错误', '#username', {
					tips : [ 2, 'red' ],
					time : 2000
				});
			}
		});

	}();

	function checkValue() {
		var username = $("#username").val();
		var userpass = $("#password").val();
		if ('' == username) {
			layer.tips('用户名不能为空', '#username', {
				tips : [ 2, 'red' ],
				time : 2000
			});
			return false;
		} else if ('' == userpass) {
			layer.tips('密码不能为空', '#password', {
				tips : [ 2, 'red' ],
				time : 2000
			});
			return false;
		} else {
			return true;
		}
		return true;
	}
</script>
</html>