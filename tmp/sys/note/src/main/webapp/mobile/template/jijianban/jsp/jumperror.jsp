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

<title>${websiteName} - 错误提示页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="${templatePathMobile}/css/bills.css" rel="stylesheet" />
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
				location.replace("<%=basePath%>");
			view.innerHTML = "<b> " + t + " </b>"; //   显示倒计时   
			t--; //   计数器递减   
		}
	</script>
	<div style="display:block;width:450px;margin:auto;">
	<div style="display:block;width:500px;float:right;margin-top:30px;">
	<span id="" style="display:block;text-align:center;"><span style="color:#f39;font-weight:700;font-size:30px;">出现错误~~</span><br/>页面将在<span id="view"></span>秒后跳转……</span>
	<p>&nbsp;</p>
	<span id="" style="display:block;text-align:center;">您正在尝试<span style="color:#f36;font-weight:600;font-size:16px;">${errpos}</span>；看到此页面说明您在这个操作过程中<span style="color:#f36;font-weight:600;font-size:16px;">${errmsg}</span></span>
	<p>&nbsp;</p>
	<span style="display:block;text-align:center;font-size:21px;line-height:30px;">等不及了<a class="returnia" onclick="location.replace('<%=basePath%>');"></a></span>
	</div>
	</div>
</body>
</html>
