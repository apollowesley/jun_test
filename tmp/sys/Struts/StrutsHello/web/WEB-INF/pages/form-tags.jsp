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
</head>
<body>
<!--
 如果theme设置为simple,则不会自动添加table,需要自己定位并加入相关的cssClass属性
 可对表单提交的值进行回显,从栈顶对象中进行匹配
 -->
<s:form action="formLogin" method="POST">
    <s:hidden name="formUser.id"></s:hidden>
    <s:textfield label="name" name="formUser.name"></s:textfield>
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
