<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>async</title>
</head>
</style>
<body style="margin: 0; overflow: hidden;width: 100%; height: 100%;">
	<%=request.getAttribute("message") == null ? "" : request.getAttribute("message") %>
	<form action="/async/UploadServlet" enctype="multipart/form-data" method="post">
		<input type="file" name="d" />
		<br/>
		<textarea id="result" class="inputStyle" wrap="off"></textarea>
		<br/>
		<input type="submit" value="提交">
	</form>

	<p></p>
	<input type="button" onclick="window.open('/async/DownloadServlet')" value="下载" />
</body>
</html>