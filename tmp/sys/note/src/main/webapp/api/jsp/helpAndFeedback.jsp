<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>页面意见反馈问卷</title>
<meta name="viewport" content="width=device-width, initial-scale = 2 ,minimum-scale = 2 ,maximum-scale = 2,user-scalable = no ,target-densitydpi = device-dpi" />
<style>
body{font-size:32px;}.textarea_content{width:100%;height:400px}.fl{float:left}.fr{float:right}.btn{width:240px;height:90px;border:0;font-size:40px;}
</style>
<script src="api/js/ajax.js"></script>
<script>
	function saveFeedback() {
		var content = encodeURI("a=98&feedbackc="+document.getElementById("input_box_area").value);
		
		/*同步cookies*/
		var ajax = new Ajax(document.forms[0].action, content);
		activity.synCookies(ajax.url + '?' + ajax.data);
		
		var result = ajax.get();
		result = (new Function('return ' + result))();
		alert(result.msg);
		activity.finish();
	}
</script>
</head>
<body class="pageBackground">
	<form id="usr_feedback_form" name="usr_feedback_form" action="Portal.api" method="post">
		<div class="ini">
			<label for="feedbackc">请留下您对页面的意见或建议(<em class="hb">必填</em>):
			</label>
			<textarea id="input_box_area" name="feedbackc" class="textarea_content"></textarea>
		</div>
		<div class="ini">
			<input type="button" class="btn fr" value="提交" onclick="javascript:saveFeedback();" />
			<div class="clear"></div>
		</div>
	</form>
	<script>
		if (document.cookie != activity.getCookies()) {
			document.cookie = activity.getCookies();
		}
	</script>
</body>
</html>