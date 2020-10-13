<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<jsp:include page="easyui_lib.jsp"></jsp:include>
</head>
<body>
<div id="win" class="easyui-window" 
	style="width:300px;height:180px;"
	title="用户登录" 
	collapsible="false" minimizable="false" maximizable="false" 
	closable="false" draggable="false">

	<form style="padding:10px 20px 10px 40px;">
		<p>用户名: <input type="text"></p>
		<p>密    码: <input type="password"></p>
		<div style="padding:5px;text-align:center;">
			<a href="javascript:void(0)" onclick="login();" class="easyui-linkbutton" icon="icon-ok">登陆</a>
		</div>
	</form>
	
</div>


<script type="text/javascript">
function login(){
	window.location = '${ctx}login/main.jsp';
}
</script>
</body>
</html>