<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会话超时</title>
</head>
<body>会话超时
<script type="text/javascript">
	//判断如果当前页面不为主框架，则将主框架进行跳转
	var tagert_URL = "login";
	if (self == top) {
		window.location.href = tagert_URL;
	} else {
		top.location.href = tagert_URL;
	}
</script>
</body>
</html>