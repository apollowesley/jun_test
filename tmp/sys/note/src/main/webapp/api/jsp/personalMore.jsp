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
<title>修改密码 - ${websiteName}</title>
<meta name="viewport" content="width=device-width, initial-scale = 1 ,minimum-scale = 1 ,maximum-scale = 1,user-scalable = no ,target-densitydpi = device-dpi" />
<style>
body{font-size:32px;text-align:center;color:#383838}
.fpwpage{margin:auto;}
.fpw_item{line-height:60px;margin:40px;}
.fpw_item input{border:1px solid #d8d8d8;height:60px;width:60%;}
.fpw_item input:focus{border:1px solid #8888ff;}
.fpw_btn{height:60px;width:50%;margin-top:60px;font-size:24px;}
</style>
<script src="api/js/ajax.js"></script>
<script>
function checkandsubmit(){
	var password_old=document.getElementById('password_old').value.trim();
	var password_new=document.getElementById('password_new').value.trim();
	var password_new2=document.getElementById('password_new2').value.trim();
	if(password_old!=''&&password_new!=''&&password_new2!=''){
		var content = encodeURI("a=37&password_old=" + password_old + "&password_new=" + password_new + "&password_new2=" + password_new2);
		/*同步cookies*/
		content+='&sessionid='+activity.getCookieValue('SESSIONID');
		var ajax = new Ajax(document.forms[0].action, content);
		var result = ajax.get();
		result = (new Function('return ' + result))();
		alert(result.msg);
		if(result.realSucess=='true')
			activity.finish();
	} else {
		alert("请输入原始密码及新密码");
	}
}
</script>
</head>

<body>
<!-- 用于能够在填写相对路径action时，获取到绝对路径 -->
<form id="usr_password_form" name="usr_password_form" action="Portal.api" method="post">
	<div class="fpwpage">
		<div class="fpw_item"><p >修改密码</p>
		<p><span>原&#8195;密&#8195;码：</span><input type="password" id="password_old"/></p>
		<p><span>新&#8195;密&#8195;码：</span><input type="password" id="password_new"/></p>
		<p><span>确认新密码：</span><input type="password" id="password_new2"/></p></div>
		<div class="fpw_item"><button class="fpw_btn" type="button" id="sub_btn"
			onclick="javascript:checkandsubmit();">修改密码</button></div>
	</div>
		<script>
		if (document.cookie != activity.getCookies()) {
			document.cookie = activity.getCookies();
		}
	</script>
</body>
</html>
