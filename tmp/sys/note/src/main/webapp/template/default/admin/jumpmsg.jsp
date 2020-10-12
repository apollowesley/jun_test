<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

<title>出错了 - ${websiteName}</title>

<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<style>.returnia{display:inline-block;background:url(img/returni.jpg) 0px 0px no-repeat;height:30px;width:85px;vertical-align:middle;cursor:pointer;}
.returnia:hover{background:url(img/returni.jpg) 0px -43px no-repeat;}
</style>
</head>

<body>
	<script>
		var t = 5;
		setInterval("testTime()", 1000); //启动1秒定时   
		function testTime() {
			if (t <= 0)
				location.replace("<%=basePath%>${msgjump}");
			view.innerHTML = "<b> " + t + " </b>"; //   显示倒计时   
			t--; //   计数器递减   
		}
	</script>
	<div style="display:block;width:500px;float:right;margin-top:30px;">
	<span>页面将在<span id="view"></span>秒后跳转……</span>
	<p>&nbsp;</p>
	<span id="" style="display:block;text-align:center;">您正在尝试<span style="color:#f36;font-weight:600;font-size:16px;">${msgpos}</span>；看到此页面说明<span style="color:#f36;font-weight:600;font-size:16px;">${msgcontent}</span></span>
	<p>&nbsp;</p>
	<span style="display:block;text-align:center;font-size:21px;line-height:30px;">等不及了<a class="returnia" onclick="location.replace('<%=basePath%>${msgjump}');"></a></span>
	</div>
</body>
</html>
