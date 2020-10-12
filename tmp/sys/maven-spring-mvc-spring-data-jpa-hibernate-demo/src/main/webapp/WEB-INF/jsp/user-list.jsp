<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User List</title>
</head>
<body>

<h1>User List</h1>
${message}
<table border="1">
	<tr>
		<td>Id</td>
		<td>Name</td>
		<td>age</td>
		<td>#</td>
	</tr>
	<c:forEach var="user" items="${userList}">
	<tr>
		<td>${user.id }</td>
		<td>${user.name }</td>
		<td>${user.age }</td>
		<td><a href="<%=request.getContextPath() %>/user/edit/${user.id}">edit</a></td>
	</tr>
	</c:forEach>
</table>

</body>
</html>