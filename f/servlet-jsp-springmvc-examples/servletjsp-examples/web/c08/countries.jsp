<%--
  Created by IntelliJ IDEA.
  User: joey
  Date: 2020/4/11
  Time: 2:21 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Countries</title>
</head>
<body>
We operate in these countries:
<ul>
    <c:forEach items="${countries}" var="country">
        <li>${country.value}</li>
    </c:forEach>
</ul>

</body>
</html>
