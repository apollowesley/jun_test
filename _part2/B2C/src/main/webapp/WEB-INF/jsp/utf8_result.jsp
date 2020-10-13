<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>响应参数页面</title>

<style type="text/css">
body table tr td {
	font-size: 14px;
	word-wrap: break-word;
	word-break: break-all;
	empty-cells: show;
}
</style>
</head>
<body>
<%-- 	<table width="800px" border="1" align="center">
		<tr>
			<th colspan="2" align="center">响应结果</th>
		</tr>
		${result }
	</table> --%>
	
	<table width="800px" border="1" align="center">
		<tr>
			<th>参数名</th>
			<th>值</th>
		</tr>  
		<c:forEach items="${map}" var="mymap" >  
			<tr>
				<td width="30%"><c:out value="${mymap.key}" />  </td>
				<td><c:out value="${mymap.value}" />  </td>
			</tr>  
		</c:forEach>
		
	</table>
</body>
</html>