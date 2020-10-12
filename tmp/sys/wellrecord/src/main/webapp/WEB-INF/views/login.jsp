<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AOP日志记录/权限控制-模块测试</title>
</head>
<body>
	<c:choose>
		<c:when test="${false == isLogin}">
			RecordAnno注解测试：<br/>
			tailNo: ${tailNo} <br/>
			tailNos: ${tailNos} <br/>
			TailDto.tailNo: ${tailNoINTailDto} <br/>
			TailDto.tailNos: ${tailNosINTailDto} <br/>
		</c:when>
		<c:otherwise>
			正在登录...
		</c:otherwise>
	</c:choose>
</body>
</html>