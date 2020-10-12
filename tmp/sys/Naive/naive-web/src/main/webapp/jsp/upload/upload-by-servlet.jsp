<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.inc.jsp"%>
<html>
<head>
<title>文件上传界面</title>
<%@ include file="/common/head.inc.jsp"%>
</head>
<body style="margin: 0 auto;">
<h1>请选择上传文件</h1>
<form method="post" action="${webroot}/UploadBySpringAction.action?method=handleUpload" enctype="multipart/form-data" >
	选择文件：<input type="file" name="files" /> <br/>
	描述信息: <input type="text" name="desc"/> <br/>
	<input type="reset" />&nbsp;<input type="submit" /> <br/>
	<a href="#" onclick="javascript:history.go(-1); return false;">返回</a> <br/>
	<c:if test="${msg != null}"><span class>${msg}</span></c:if>
</form>
	
	
</div>
<script type="text/javascript" src="${webroot}/styles/js/jquery/jquery-1.7.2.min.js"></script>	
</body>
</html>
