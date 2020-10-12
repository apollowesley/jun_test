<%@ page import="com.gs.struts.Product" %><%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 2/26/16
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<s:debug></s:debug>
<s:property />

<br />

<s:url value="/product/getProduct" var="url">
    <s:param name="id" value="1001" /><!-- 这个value可以直接来自于值栈,自动进行ognl解析,如值栈中有个属性值为productId,则value="productId",如果value中要使用字符串,则直接把字符串用单引号 -->
</s:url>

${url }
<br />
<s:url action="exception" namespace="/exception" var="url1"><!-- includeParams属性表示是否包含请求参数: none, get, all -->

</s:url>

${url1 }
<br />

<!-- 向page, request, session, application中设置值.这里的value也是使用ognl -->
<s:set name="setname" value="'value'" scope="request"></s:set>
s:set->${requestScope.setname }
<br />
<!-- 在标签开始时压入值栈,标签结束时,弹出 -->
<%
    Product product = new Product();
    product.setId(1000);
    product.setName("product name");
    request.setAttribute("product" , product);
%>
<s:push value="#request.product">
    Product Name: ${product.name } or ${name }
</s:push>

<br />

<!-- ognl解析 -->
<s:if test="price <= 500">price <= 500: ${price }</s:if>
<s:elseif test="price > 500 and price <=1000">price > 500 and price <= 1000: ${price }</s:elseif>
<s:else>price other: ${price }</s:else>

<br />
All products from list:<br />
<s:iterator value="products" var="p" status="status">
    ${status.index + 1}: ${id}, ${p.name} <br /><!-- status: index, count, odd, even, first, last -->
</s:iterator>
<br />

<s:sort comparator="productComparator" source="products" var="pr">
    <s:iterator value="#attr.pr" var="pro" status="status">
        ${pro.id}: ${pro.name}<br />
    </s:iterator>
</s:sort>
<br />
<s:date name="date" var="myDate" format="yyyy-MM-dd HH:mm:ss"></s:date>
${myDate }<br/>

<s:iterator value="products" var="prod" status="status">
    <s:a href="/product/getProduct?name=%{name}">${name}</s:a><!-- 在些标签中使用%{name}的方式强制使用ognl解析 -->
</s:iterator>

</body>
</html>
