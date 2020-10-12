<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>找回密码 - ${websiteName}</title>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<style>
.fpwpage{width:950px;margin:auto;}
.hd {padding: 10px 0;}
.hd h2{font-size:18px;}
.fpw_item{height:30px;line-height:30px;margin-left:120px;}
.fpw_item span{width:110px;display:inline;float:left;text-align:right;padding-right:10px;font-size:14px;}
.fpw_item input{width:186px;line-height:18px;font-size:14px;margin-right:10px;height:18px;vertical-align:middle;font-size:12px;}
.fpw_btn{width:50px;margin-left:120px;height:24px;line-height:24px;}
.fpw_item .confirm_box input{margin-left:120px;width:14px;height:14px;line-height:16px;vertical-align:middle;margin-right:8px;}
.fpw_item .confirm_box span{height:16px;line-height:16px;vertical-align:middle;}
</style>
<script>
function checkandsubmit(){
	var a_u=document.getElementById('email').value;
	if(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.exec(a_u))
	{location.href='<%=basePath%>ForgetPassword.yy?email='+a_u;}
}
</script>
</head>

<body>
<%@ include file="./../html/topbar.html"%>
	<div class="fpwpage">
		<div class="hd">
			<h2>找回密码</h2>
		</div>
		<div class="fpw_item"><span>邮箱</span><input type="text" id="email"/>请输入注册账户时的邮箱</div>
		<div class="fpw_item"><span>验证码</span><input type="text"/>请输入验证码</div>
		<div class="fpw_item"><div class="confirm_box"><input id="confirm_box" type="checkbox" onclick="if(this.checked){document.getElementById('sub_btn').removeAttribute('disabled');}else{document.getElementById('sub_btn').setAttribute('disabled','disabled');}"/><lable>系统会自动发送有密码的邮件到您的邮箱，请您查收后重新登录并修改密码。</lable></div></div>
		<div class="fpw_item"><button class="fpw_btn" type="button" id="sub_btn" disabled="disabled"
			onclick="javascript:checkandsubmit();">提交</button></div>
	</div>
</body>
</html>
