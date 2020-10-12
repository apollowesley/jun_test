<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>列表页面</title>
</head>
<body>
    <h2>列表页面</h2>
    <c:forEach items="${employs}" var="item" varStatus="id">
        <p>序号:${id.index}===编号${item.empno}=======姓名${item.ename}</p>
    </c:forEach>

</body>
</html>
