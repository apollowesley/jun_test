<%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 6:03 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Form</title>
</head>
<body>
<form action="test2.jsp" method="post">
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="address"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Login">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
