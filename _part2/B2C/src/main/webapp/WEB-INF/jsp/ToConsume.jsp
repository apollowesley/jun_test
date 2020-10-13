<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<form id="pay_form" action="${requestFrontUrl }" method="post">
	
		<c:forEach items="${map}" var="mapItem" >
			<input type="hidden" name="${mapItem.key}" id="${mapItem.key}" value="${mapItem.value}" />  
		</c:forEach>
	</form>
</body>
<script type="text/javascript">document.all.pay_form.submit();</script>
</html>