<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 2/26/16
  Time: 09:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <a href="<%=path %>/product/input.action">Product input</a>
    <br />
    <a href="<%=path %>/user/tologin.action">User login</a>
    <br />
    <a href="<%=path %>/user/save.action">Save user</a>
    <br />
    <a href="<%=path %>/user/update.action">Update user</a>
    <br />
    <a href="<%=path %>/user/delete.action">Delete user</a>
    <br />
    <a href="<%=path %>/user/query.action">Query user</a>
    <br />
    <a href="<%=path %>/context/context.action?pname=pname">ActionContext</a>
    <br />
    <a href="<%=path %>/aware/aware.action?pname=pname">Aware</a>
    <br />
    <a href="<%=path %>/dynamic/dynamic!save.action">Dynamic save</a>
    <br />
    <a href="<%=path %>/dynamic/dynamic!update.action">Dynamic update</a>
    <br />
    <a href="<%=path %>/ognl/ognl.action">OGNL</a>
    <br />
    <a href="<%=path %>/exception/exception.action">Exception</a>
    <br />
    <a href="<%=path %>/exception/globalException.action">Global Exception</a>
    <br />
    <a href="<%=path %>/tag/tag.action">Tag</a>
    <br />
    <a href="<%=path %>/form/toFormLogin.action">Form Tag login</a>
    <br />
    <a href="<%=path %>/convertion/convertion.action">Convertion</a>
    <br />
    <a href="<%=path %>/convertion/toPage.action">Convertion1</a>
    <br />
    <a href="<%=path %>/i18n/toI18NPage.action">i18n</a>
  </body>
</html>
