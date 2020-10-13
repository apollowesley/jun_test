<%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 12:10 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
</head>
<body>
accept-languageL ${header['accept-language']}
<br>
session id: ${pageContext.session.id}
<br>
employee: ${requestScope.employee.name}, ${employee.address.city}
<br>
capital: ${capitals["China"]}
</body>
</html>
