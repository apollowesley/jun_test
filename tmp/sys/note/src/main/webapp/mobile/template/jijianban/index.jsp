<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=0.67,maximum-scale=0.67,minimum-scale=0.67" />
<title>记账本 - 永远免费的在线记账</title>
<link href="${templatePathMobile}/css/common.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript">
	var uoid="<%=session.getAttribute("UOID")%>";//返回值为对象，转换成字符串后为'null'(一个内容为null的字符串)
	//alert(uoid);
	if(!(uoid==""||uoid=="null"))
	{
		location.href="<%=basePath%>"+"mobile/Bills.yy";
	}
	</script>
	<div data-role="page" data-theme="f" class="page">
		<div id="g_header">
			<div class="g_left_nav_toolbar"><ul><li>&nbsp;</li></ul></div>
			<div class="g_middle_nav_toolbar">登录</div>
			<div class="g_right_nav_toolbar"><ul><li>&nbsp;</li></ul></div>
		</div>
		<style>
#g_isrcollWrapper {	bottom: 0px;}
</style>
<form name="login_form" id="login_form" action="<%=basePath%>mobile/Login.yy" method="post"  onsubmit="login();">
		<div id="g_isrcollWrapper">
			<div id="setting_wp" class="mc">
				<ul class="lv_4">
					<li>
						<div class="col2">
							昵称:<input name="username" type="text" id="username"
								class="login_txt" autofocus />
						</div>
					</li>
					<li class="nb">
						<div class="col2">
							密码:<input name="password" type="password" id="password"
								class="login_txt" />
						</div>
					</li>
				</ul>
				<div class="loginbar">
					<button class="btn_login" onclick="document.form[0].submit();">登录</button>
				</div>
			</div>
		</div>
		</form>
		<%-- <div>
			<div style="display:none">
				<script language="javascript" type="text/javascript"
					src="http://js.users.51.la/439714.js"></script>
				<noscript>
					<a href="http://www.51.la/?439714" target="_blank"><img alt=""
						src="http://img.users.51.la/439714.asp" style="border:none" /></a>
				</noscript>
			</div>
		</div>--%>
	</div>
</body>
</html>