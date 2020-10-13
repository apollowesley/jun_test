<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查询结果页面</title>

</head>
<body>
		<c:forEach items="${rspData}" var="mymap" >  
<%-- 			<c:if test="${mymap.key=='respCode'}">
				<c:if test="${mymap.value=='00'}">
				查询成功
				</c:if>
			</c:if> --%>
			<c:if test="${mymap.key=='origRespCode'}">
<%-- 				<c:if test="${mymap.value=='00'}">
				查询结果：交易成功
				</c:if> --%>
				<c:choose>
				<c:when test="${mymap.value=='00'}">
	              	查询结果：交易成功
	       		</c:when>
	       		<c:otherwise>
	              	查询结果：交易失败
	       		</c:otherwise>
				</c:choose>
			</c:if>
	       
		</c:forEach>
		<br><br><br>
	<table width="800px" border="1" align="center">
		<tr>
		<th colspan="2" align="left">参数列表</th>
		</tr>
		<tr>
			<th>参数名</th>
			<th>值</th>
		</tr>
		<c:forEach items="${rspData}" var="mymap" >  
			<tr>
				<td width="30%"><c:out value="${mymap.key}" />  </td>
				<td><c:out value="${mymap.value}" />  </td>
			</tr>  
		</c:forEach>
		
	</table>
	
</body>
</html>