<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AOP日志记录/权限控制-模块测试</title>
</head>
<body>

	<h1>服务器时间：${serverTime}</h1>
	
	<br/>
	
	<a href="${pageContext.request.contextPath}/test.do">简单的测试跳转</a>
	&emsp;
	<a href="javascript:void(0);" onclick="javascript:loadPage();">携带参数跳转</a>

	<div id="testRecordAnnoResult"></div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
		function loadPage() {
			$('#testRecordAnnoResult').load('${pageContext.request.contextPath}/testRecordAnno.do', {
						tailNo : 'B-2051',
						tailNos : 'B-2256'
					});
		}
	</script>
</body>
</html>
