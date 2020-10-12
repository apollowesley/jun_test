<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 3/4/16
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
    <link href="<%=path %>/public/css/main.css" rel="stylesheet" />
</head>
<body>
<!--
 form的主题
 风格相近的模板被打包成一个主题
 simple:简单的,没有任何效果的
 xhtml:默认的主题,表单会使用table
 css_xhtml:使用css样式
 ajax:以xhtml为模板,增加了一些ajax功能

 可在s:form标签中指定theme属性或者为单独的元素指定想要的theme
 或在page, request, session, application中放入theme属性,比全局属性具有更高的优先级
 或在struts的default.properties文件中的struts.ui.theme属性,适用于所有的s:form标签
 -->
<s:form action="formLogin" method="POST" theme="simple">
    <s:hidden name="formUser.id"></s:hidden>
    <s:textfield label="name" name="formUser.name" cssClass="name"></s:textfield>
    <s:password label="pwd" name="formUser.pwd"></s:password>
    <s:textarea label="desc" name="formUser.desc"></s:textarea>

    <s:checkbox label="Accept" name="formUser.accept"></s:checkbox><!-- 用于提交一个布尔值 -->

    <s:radio list="#{'0':'male', '1':'female'}" label="Gender" name="formUser.gender"></s:radio>

    <!-- 使用List<String>（集合类型）接收name为addresses的checkboxlist提交的值 -->
    <s:checkboxlist list="formUser.addresses" listKey="id" listValue="detail" label="address" name="addresses"></s:checkboxlist>

    <s:select list="{11, 12, 13, 14}" headerKey="" headerValue="Select one..." label="age" name="formUser.age">
        <s:optgroup label="20-30" list="#{20:20, 21:21, 22:22}"></s:optgroup><!-- 必须指定键值对 -->
    </s:select>

    <s:submit value="Confirm"/>
</s:form>
</body>
</html>
