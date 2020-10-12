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
<meta name="viewport" content="width=device-width, initial-scale = 1 ,minimum-scale = 1 ,maximum-scale = 1,user-scalable = no ,target-densitydpi = device-dpi" />
<style>
body{font-size:32px;text-align:center;}
.fpwpage{margin:auto;}
.fpw_item{height:120px;line-height:60px;margin:40px;}
.fpw_item input{height:60px;width:100%;}
.fpw_btn{height:60px;width:50%;margin-top:60px;}
</style>
<script src="api/js/ajax.js"></script>
<script>
function checkandsubmit(){
	var a_u=document.getElementById('email').value;
	if(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.exec(a_u)){
		var content = encodeURI(a_u);
		var ajax = new Ajax('Portal.api', "a=103&email=" + content + "&date=");
		var result = ajax.get();
		result = (new Function('return ' + result))();
		alert(result.msg);
		if(result.realSucess=='true')
			activity.finish();
	} else {
		alert("请输入正确的邮箱");
	}
}
</script>
</head>

<body>
	<div class="fpwpage">
		<div class="fpw_item"><p >请输入注册账户时的邮箱</p><p><input type="text" id="email"/></p></div>
		<div class="fpw_item"><button class="fpw_btn" type="button" id="sub_btn"
			onclick="javascript:checkandsubmit();">提交</button></div>
	</div>
</body>
</html>
