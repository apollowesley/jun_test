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

<h1>Edit User</h1>

${message }

<form action="<%=request.getContextPath() %>/user/edit" modelAttribute="user" method="post">
<table border="1">
	<tr><td>Id</td><td><input type="hidden" name="id" value="${user.id}" />${user.id }</td></tr>
	<tr><td>Name</td><td><input type="text" name="name" value="${user.name}" /></td></tr>
	<tr><td>age</td><td><input type="text" name="age" value="${user.age}" /></td></tr>
	<tr>
		<td colspan="2"><button type="submit">submit</button></td>
	</tr>
</table>
</form>

</body>
</html>