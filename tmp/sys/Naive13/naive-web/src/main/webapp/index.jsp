<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.inc.jsp"%>
<html>
<head>
<%@ include file="/common/head.inc.jsp"%>
</head>
<meta http-equiv="Expires" content="0" /> 
<body>
<h2>Hello World!</h2>

<a href="${webroot}/IndexAction.action?method=usualRequest">Redirector Action</a>
<a id="ajaxLint" href="#">Ajax Request</a>
<a id="userCount" href="#">User Count</a>
<a id="fileUpload" href="${webroot}/UploadBySpringAction.action?method=toUpload">File Upload By Spring</a>
<br/>
<a href="${webroot}/UserMgrAction.action?method=toUserList">UserList</a>
<div id="area" style="display: none; width: 200px; height: 100px; border: 1px red;"></div>
<script type="text/javascript" src="${webroot}/styles/js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
$(function() {

$('#ajaxLint').click(function() {
	$.ajax({
		url: webroot + '/IndexAction.action?method=ajaxRequest',
		data:{},
		type:'POST',
		dataType:'json',
		success:function(data) {
			alert(data.flag + ", " + data.msg);			
		}
	});	
});
$('#userCount').click(function() {
	$.ajax({
		url: webroot + '/IndexAction.action?method=getUserCount',
		data:{},
		type:'POST',
		dataType:'json',
		success:function(data) {
			alert(data.flag + ", 总人数:" + data.msg);			
		}
	});	
});
	
});

</script>
</body>
</html>
