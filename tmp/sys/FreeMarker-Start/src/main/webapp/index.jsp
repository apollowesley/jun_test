<%--
  Created by IntelliJ IDEA.
  User: liuburu
  Date: 2018/1/8
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // springmvc
    String contextPath = request.getContextPath();
    // http://localhost:8080/springmvc/
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
    request.setAttribute("ctx", basePath);
%>
<html>
<head>
    <title>欢迎页面</title>
</head>
<body>
This World Is Beautiful.  ---KiWiPeach
<hr>
<h3><a href="${ctx}/demo/freemarker">00：Hello Freemarker页面</a></h3>
<hr>
<h3><a href="${ctx}/demo/freemarker01">01：Freemarker基本数据类型取值</a></h3>
<hr>
<h3><a href="${ctx}/demo/freemarker02">02：Freemarker自定义数据类型</a></h3>
<hr>
<h3><a href="${ctx}/demo/freemarker03">03：Freemarker集合（Collection），Map遍历</a></h3>
<hr>
<h3><a href="${ctx}/demo/freemarker04">04：变量的赋值与运算</a></h3>
<hr>
<h3><a href="${ctx}/demo/freemarker05">05：if,Switch语法</a></h3>
<hr>
<h3><a href="${ctx}/demo/freemarker06">06：字符串的内嵌函数</a></h3>
<hr>
<h3><a href="${ctx}/demo/freemarker07">07：自定义函数</a></h3>
<p> 自定义函数需要实现TemplateMethodModelEx接口，实现exec方法，将freemarker类型转成java类型</p>
<h3><a href="${ctx}/demo/freemarker08">08：自定义指令</a></h3>
<p> 自定义函数需要实现cn.kiwipeach.demo.freemarker.instructions.CustomPermissionTemplate接口，实现execute方法，将freemarker类型转成java类型</p>
<h3><a href="${ctx}/demo/freemarker09">09：其他常见内建函数</a></h3>
<h3><a href="${ctx}/demo/freemarker10">10：宏macro   nested    function  指令</a></h3>
</body>
</html>
